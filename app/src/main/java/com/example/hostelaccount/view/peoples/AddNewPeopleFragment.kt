package com.example.hostelaccount.view.peoples

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.FragmentAccountingAddNewEntryBinding
import com.example.hostelaccount.databinding.FragmentAddNewPeopleBinding
import com.example.hostelaccount.db.AccountingItemModel
import com.example.hostelaccount.db.DbManager
import com.example.hostelaccount.db.PeopleItemModel
import com.example.hostelaccount.view.accounting.AccountingAddNewEntryFragment
import com.example.hostelaccount.view.accounting.AccountingListFragment

class AddNewPeopleFragment : Fragment() {
    lateinit var binding: FragmentAddNewPeopleBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {
        binding = FragmentAddNewPeopleBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // определение переменной БД
        val db = DbManager.getInstance(requireActivity())

        // слушатель нажатий на кнопку сохранить
        binding.btnSave.setOnClickListener {
            // создание переменной с введёнными данными
            val peopleItem = PeopleItemModel(null,
                binding.txtRoomNumNewMan.text.toString(),
                binding.txtNameNewMan.text.toString(),
                binding.txtPlDateFrom.text.toString(),
                binding.txtPlDateTo.text.toString(),
                binding.switchUsMan.isChecked,
                binding.txtEdAddInfo.text.toString()
            )
            // запуск нового потока для асинхронного сохранения данных в БД
            Thread {
                db.peopleDao().insertAll(peopleItem) // сохранение
                // запуск первого фрагмента после сохранения
                startPeoplesListFragment()
            }.start()
        }


    }

    companion object {
        @JvmStatic
        fun newInstance() = AddNewPeopleFragment()
    }

    fun startPeoplesListFragment() { // функция запуска первого фрагмента
        @Suppress("DEPRECATION")
        fragmentManager?.beginTransaction()?.replace(R.id.fragmentLayoutPeoples, ListRoomsFragment.newInstance(), "TAG")!!
            .commit()
    }

}