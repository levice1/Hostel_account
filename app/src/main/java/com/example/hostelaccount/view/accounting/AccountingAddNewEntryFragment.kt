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
import com.example.hostelaccount.db.local.AccountingItemModel
import com.example.hostelaccount.db.local.DbManager
import com.example.hostelaccount.model.AccountingViewModel
import com.example.hostelaccount.view.FragmentManageHelper
import com.example.hostelaccount.viewmodel.ProcessingDate
import com.example.hostelaccount.viewmodel.ValidationInputData


class AccountingAddNewEntryFragment : Fragment() {

    private lateinit var binding: FragmentAccountingAddNewEntryBinding

    private lateinit var viewModel: AccountingViewModel

    private val validationInputData = ValidationInputData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {
        binding = FragmentAccountingAddNewEntryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        viewModel.clearData()
    }

    // Функция для отображения короткого сообщения
    private fun showToast(msg: Int) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // определение viewModel для приёма данных от фрагмента
        viewModel = ViewModelProvider(requireActivity())[AccountingViewModel::class.java]
        // определение переменной БД
        val db = DbManager.getInstance(requireActivity())
        //  определение объекта viewModel и получение данных
        val inputData = viewModel.getData()

        //  ЕСЛИ БЫЛИ ПЕРЕДАНЫ ДАННЫЕ ИЗ ДРУГОГО ФРАГМЕНТА, ТО ЗАПОЛНЯЕТ ПОЛЯ АВТОМАТИЧЕСКИ
        if (inputData != null) {
            binding.btnDelete.visibility = View.VISIBLE
            binding.txtPlSum.setText(inputData.sum.toString())
            binding.txtPlDate.setText(inputData.date)
            binding.txtPlWhoOrWhat.setText(inputData.reason)
            binding.switchPlusOrMinus.isChecked = inputData.profit
            // и запускает слушатель на кнопку удаления
            binding.btnDelete.setOnClickListener {
                Thread {
                    db.accountingDao().deleteById(inputData.id!!)
                    FragmentManageHelper(parentFragmentManager)
                        .initFragment(
                            R.id.fragmentLayoutAccounting,
                            AccountingListFragment.newInstance()
                        )
                }.start()
            }
        } else {
            // Установка текущей даты для удобства
            binding.txtPlDate.setText(ProcessingDate().getCurrentDate())
        }

        // слушатель нажатий на кнопку сохранить
        binding.btnSave.setOnClickListener {
            // ЕСЛИ БЫЛИ ПЕРЕДАНЫ ДАННЫЕ, ТО ID ПРИСВАЕВАЕТ ТОТ ЧТО БЫЛ ПЕРЕДАН. ЕСЛИ НЕТ ТО NULL
            var id: Int? = null
            if (inputData?.id != null) id = inputData.id

            val date = binding.txtPlDate.text.toString()
            val reason = binding.txtPlWhoOrWhat.text.toString().replaceFirstChar { it.uppercase() }
            val sum = binding.txtPlSum.text.toString()
            val profit = binding.switchPlusOrMinus.isChecked

            when {
                !validationInputData.validateNameStr(reason) -> showToast(R.string.error_reason_required)
                !validationInputData.validateDateStr(date) -> showToast(R.string.error_date_required)
                !validationInputData.validateIntNum(sum) -> showToast(R.string.error_sum_required)
                else -> {
                    // создание переменной с введёнными данными
                    val accountingItem = AccountingItemModel( id, date, reason, sum.toInt(), profit )
                    // запуск нового потока для асинхронного сохранения данных в БД
                    Thread {
                        db.accountingDao().insertAll(accountingItem) // сохранение
                        // запуск первого фрагмента после сохранения
                        FragmentManageHelper(parentFragmentManager)
                            .initFragment(
                                R.id.fragmentLayoutAccounting,
                                AccountingListFragment.newInstance()
                            )
                    }.start()
                }
            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() = AccountingAddNewEntryFragment()
    }
}


