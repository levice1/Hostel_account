package com.example.hostelaccount.view.accounting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.FragmentAccountingAddNewEntryBinding
import com.example.hostelaccount.db.local.AccountingItemModel
import com.example.hostelaccount.db.local.DbManager
import com.example.hostelaccount.model.AccountingViewModel
import com.example.hostelaccount.view.FragmentManageHelper
import com.example.hostelaccount.viewmodel.ProcessingDate


class AccountingAddNewEntryFragment : Fragment() {

    lateinit var binding: FragmentAccountingAddNewEntryBinding

    lateinit var viewModel: AccountingViewModel

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // определение viewModel для приёма данных от фрагмента
        viewModel = ViewModelProvider(requireActivity()).get(AccountingViewModel::class.java)
        // определение переменной БД
        val db = DbManager.getInstance(requireActivity())
        //  определение объекта viewModel и получение данных
        val inputData = viewModel.getData()

        //  ЕСЛИ БЫЛИ ПЕРЕДАНЫ ДАННЫЕ ИЗ ДРУГОГО ФРАГМЕНТА, ТО ЗАПОЛНЯЕТ ПОЛЯ АВТОМАТИЧЕСКИ
        if (inputData != null){
            binding.btnDelete.visibility = View.VISIBLE
            binding.txtPlSum.setText(inputData.sum.toString())
            binding.txtPlDate.setText(inputData.date.toString())
            binding.txtPlWhoOrWhat.setText(inputData.reason.toString())
            binding.switchPlusOrMinus.isChecked = inputData.profit
            // и запускает слушатель на кнопку удаления
            binding.btnDelete.setOnClickListener {
                Thread {
                    db.accountingDao().deleteById(inputData.id!!)
                    FragmentManageHelper(parentFragmentManager)
                        .initFragment(R.id.fragmentLayoutAccounting, AccountingListFragment.newInstance())
                }.start()
            }
        } else {
            // Установка текущей даты для удобства
            binding.txtPlDate.setText(ProcessingDate().getCurrentDate())
        }

        // слушатель нажатий на кнопку сохранить
        binding.btnSave.setOnClickListener {
            // ЕСЛИ БЫЛИ ПЕРЕДАНЫ ДАННЫЕ, ТО ID ПРИСВАЕВАЕТ ТОТ ЧТО БЫЛ ПЕРЕДАН. ЕСЛИ НЕТ ТО NULL
            var id :Int? = null
            if (inputData?.id != null) id = inputData.id

            // создание переменной с введёнными данными
            val accountingItem = AccountingItemModel(id,
                binding.txtPlDate.text.toString(),
                binding.txtPlWhoOrWhat.text.toString(),
                binding.txtPlSum.text.toString().toInt(),
                binding.switchPlusOrMinus.isChecked
            )
            // запуск нового потока для асинхронного сохранения данных в БД
            Thread {
                db.accountingDao().insertAll(accountingItem) // сохранение
                // запуск первого фрагмента после сохранения
                FragmentManageHelper(parentFragmentManager)
                    .initFragment(R.id.fragmentLayoutAccounting, AccountingListFragment.newInstance())
            }.start()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AccountingAddNewEntryFragment()
    }
}


