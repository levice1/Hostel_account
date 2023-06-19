package com.example.hostelaccount.view.accounting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.FragmentAccountingAddNewEntryBinding
import com.example.hostelaccount.data.data_sourse.AccountingItemModel
import com.example.hostelaccount.viewmodel.accounting.AccountingViewModel
import com.example.hostelaccount.viewmodel.util.FragmentManageHelper
import com.example.hostelaccount.viewmodel.util.ProcessingDate
import com.example.hostelaccount.viewmodel.util.ValidationInputData
import kotlinx.coroutines.*


class AccountingAddNewEntryFragment : Fragment() {
    private lateinit var binding: FragmentAccountingAddNewEntryBinding
    private lateinit var viewModel: AccountingViewModel
    private val validationInputData = ValidationInputData()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountingAddNewEntryBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // определение viewModel для приёма данных от фрагмента
        viewModel = ViewModelProvider(requireActivity())[AccountingViewModel::class.java]
        //  определение объекта viewModel и получение данных
        val inputData = viewModel.getTempItem()
        //  ЕСЛИ БЫЛИ ПЕРЕДАНЫ ДАННЫЕ ИЗ ДРУГОГО ФРАГМЕНТА, ТО ЗАПОЛНЯЕТ ПОЛЯ АВТОМАТИЧЕСКИ
        if (inputData != null) {
            fillInputFeelds(inputData)
            // и запускает слушатель на кнопку удаления
            initDelBtn(inputData)
        } else {
            // если не было переданных данных
            // Установка текущей даты для удобства
            binding.txtPlDate.setText(ProcessingDate().getCurrentDate())
        }
        // слушатель нажатий на кнопку сохранить
        initSaveBtn(inputData)
    }


    companion object {
        @JvmStatic
        fun newInstance() = AccountingAddNewEntryFragment()
    }


    // функция заполнения полей если данный пришли
    private fun fillInputFeelds(inputData: AccountingItemModel) {
        binding.btnDelete.visibility = View.VISIBLE
        binding.txtPlSum.setText(inputData.sum.toString())
        binding.txtPlDate.setText(inputData.date)
        binding.txtPlWhoOrWhat.setText(inputData.reason)
        binding.switchPlusOrMinus.isChecked = inputData.profit
    }


    // функция инициализации слушателя нажатий на кнопку удалить
    private fun initDelBtn(item: AccountingItemModel) {
        binding.btnDelete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.deleteAccountingItem(item)
            }
            FragmentManageHelper(parentFragmentManager)
                .initFragment(
                    R.id.fragmentLayoutAccounting,
                    AccountingListFragment.newInstance()
                )
        }
    }


    // функция инициализации слушателя нажатий на кнопку сохранить
    private fun initSaveBtn(inputData: AccountingItemModel?) {
        binding.btnSave.setOnClickListener {
            // ЕСЛИ БЫЛИ ПЕРЕДАНЫ ДАННЫЕ, ТО ID ПРИСВАЕВАЕТ ТОТ ЧТО БЫЛ ПЕРЕДАН. ЕСЛИ НЕТ ТО NULL
            val id = inputData?.id
            val date = binding.txtPlDate.text.toString()
            val reason = binding.txtPlWhoOrWhat.text.toString().replaceFirstChar { it.uppercase() }
                .replace("ʼ", "").replace("'", "").trim()
            val sum = binding.txtPlSum.text.toString()
            val profit = binding.switchPlusOrMinus.isChecked

            when {
                !validationInputData.validateNameStr(reason) -> showToast(R.string.error_reason_required)
                !validationInputData.validateDateStr(date) -> showToast(R.string.error_date_required)
                !validationInputData.validateIntNum(sum) -> showToast(R.string.error_sum_required)
                else -> {
                    // если данные прошли валидацию
                    // создание переменной с введёнными данными
                    val accountingItem = AccountingItemModel(id, date, reason, sum.toInt(), profit)
                    // запуск корутины для асинхронного сохранения данных в БД
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.saveAccountingItem(accountingItem)
                    }
                    // запуск первого фрагмента после сохранения
                    FragmentManageHelper(parentFragmentManager)
                        .initFragment(
                            R.id.fragmentLayoutAccounting,
                            AccountingListFragment.newInstance()
                        )
                }
            }
        }
    }


    // Функция для отображения короткого сообщения
    private fun showToast(msg: Int) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }
}


