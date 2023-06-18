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
import com.example.hostelaccount.data.data_sourse.DbManager
import com.example.hostelaccount.data.remote.BackendConstants
import com.example.hostelaccount.data.remote.InsertLocalDBToRemoteDB
import com.example.hostelaccount.viewmodel.Accounting.AccountingViewModel
import com.example.hostelaccount.viewmodel.util.FragmentManageHelper
import com.example.hostelaccount.viewmodel.util.ProcessingDate
import com.example.hostelaccount.viewmodel.util.ValidationInputData
import kotlinx.coroutines.*


class AccountingAddNewEntryFragment : Fragment() {

    private lateinit var binding: FragmentAccountingAddNewEntryBinding

    private lateinit var viewModel: AccountingViewModel

    private val validInpData = ValidationInputData()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {
        binding = FragmentAccountingAddNewEntryBinding.inflate(layoutInflater)
        return binding.root
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
            fillInputFeelds(inputData)
            // и запускает слушатель на кнопку удаления
            initDelBtn(inputData, db)
            } else {
                // если не было переданных данных
                // Установка текущей даты для удобства
                binding.txtPlDate.setText(ProcessingDate().getCurrentDate())
            }
        // слушатель нажатий на кнопку сохранить
        initSaveBtn(inputData, db)
    }


    override fun onStop() {
        super.onStop()
        viewModel.clearData()
    }


    companion object {
        @JvmStatic
        fun newInstance() = AccountingAddNewEntryFragment()
    }


    // функция заполнения полей если данный пришли
    private fun fillInputFeelds(inputData: AccountingItemModel){
        binding.btnDelete.visibility = View.VISIBLE
        binding.txtPlSum.setText(inputData.sum.toString())
        binding.txtPlDate.setText(inputData.date)
        binding.txtPlWhoOrWhat.setText(inputData.reason)
        binding.switchPlusOrMinus.isChecked = inputData.profit
    }


    // функция инициализации слушателя нажатий на кнопку удалить
    @OptIn(DelicateCoroutinesApi::class)
    fun initDelBtn(inputData: AccountingItemModel, db: DbManager){
        binding.btnDelete.setOnClickListener {
            GlobalScope.launch {
                db.accountingDao().deleteById(inputData.id!!)
                InsertLocalDBToRemoteDB(BackendConstants().deleteAcc).insertToAccounting(inputData)
            }
            FragmentManageHelper(parentFragmentManager)
                .initFragment(
                    R.id.fragmentLayoutAccounting,
                    AccountingListFragment.newInstance()
                )
        }
    }


    // функция инициализации слушателя нажатий на кнопку сохранить
    @OptIn(DelicateCoroutinesApi::class)
    fun initSaveBtn(inputData: AccountingItemModel?, db: DbManager){
        binding.btnSave.setOnClickListener {
            // ЕСЛИ БЫЛИ ПЕРЕДАНЫ ДАННЫЕ, ТО ID ПРИСВАЕВАЕТ ТОТ ЧТО БЫЛ ПЕРЕДАН. ЕСЛИ НЕТ ТО NULL
            val id = inputData?.id
            val date = binding.txtPlDate.text.toString()
            val reason = binding.txtPlWhoOrWhat.text.toString().replaceFirstChar { it.uppercase() }.replace("ʼ","").replace("'","").trim()
            val sum = binding.txtPlSum.text.toString()
            val profit = binding.switchPlusOrMinus.isChecked

            when {
                !validInpData.validateNameStr(reason) -> showToast(R.string.error_reason_required)
                !validInpData.validateDateStr(date) -> showToast(R.string.error_date_required)
                !validInpData.validateIntNum(sum) -> showToast(R.string.error_sum_required)
                else -> {
                    // если данные прошли валидацию
                    // создание переменной с введёнными данными
                    val accountingItem = AccountingItemModel( id, date, reason, sum.toInt(), profit )
                    // запуск корутины для асинхронного сохранения данных в БД
                    GlobalScope.launch {
                        // внесение в локальную БД и возврат присвоенного ID записи
                        val insertedItemId = db.accountingDao().insertItem(accountingItem)
                        // присвоение полученного ID в обьект accountingItem
                        accountingItem.id = insertedItemId[0].toInt()
                        // внесение в сетевую БД
                        InsertLocalDBToRemoteDB(BackendConstants().insertAcc).insertToAccounting(accountingItem)
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


