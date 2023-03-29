package com.example.hostelaccount.view.peoples

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.FragmentAddNewPeopleBinding
import com.example.hostelaccount.db.DbManager
import com.example.hostelaccount.db.PeopleItemModel
import com.example.hostelaccount.model.PeopleIdViewModel

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
        // определение viewModel для приёма данных от фрагмента
        val viewModel = ViewModelProvider(requireActivity()).get(PeopleIdViewModel::class.java)
        //  определение объекта viewModel и получение данных
        val inputData = viewModel.getData()
        viewModel.setData(null,null)
        // определение переменной БД
        val db = DbManager.getInstance(requireActivity())


        //  ЕСЛИ БЫЛИ ПЕРЕДАНЫ ДАННЫЕ ИЗ ДРУГОГО ФРАГМЕНТА, ТО ЗАПОЛНЯЕТ ПОЛЯ АВТОМАТИЧЕСКИ
        if (inputData != null) fillFields(inputData)
        // слушатель нажатий на кнопку сохранить
        initSaveBtnListener(inputData, db)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddNewPeopleFragment()
    }

    private fun startPeoplesListFragment() { // функция запуска первого фрагмента
        @Suppress("DEPRECATION")
        fragmentManager?.beginTransaction()?.replace(R.id.fragmentLayoutPeoples, ListRoomsFragment.newInstance(), "TAG")!!
            .commit()
    }

    private fun initSaveBtnListener(inputData: PeopleItemModel?, db: DbManager){ // функция инициализации слушателя нажатий на кнопку сохранить
        binding.btnSave.setOnClickListener {

            // ЕСЛИ БЫЛИ ПЕРЕДАНЫ ДАННЫЕ, ТО ID ПРИСВАЕВАЕТ ТОТ ЧТО БЫЛ ПЕРЕДАН. ЕСЛИ НЕТ ТО NULL
            var id :Int? = null
            if (inputData != null) id = inputData.id
            // создание переменной с введёнными данными
            val peopleItem = PeopleItemModel(id,
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

    private fun fillFields(people: PeopleItemModel ){
        binding.txtNameNewMan.setText(people.guestName)
        binding.txtRoomNumNewMan.setText(people.roomNumber)
        binding.txtPlDateFrom.setText(people.liveFrom)
        binding.txtPlDateTo.setText(people.liveTo)
        binding.txtEdAddInfo.setText(people.addInfo)
        binding.switchUsMan.isChecked = people.usPeople
    }
}