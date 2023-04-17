package com.example.hostelaccount.view.peoples

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.FragmentAddNewPeopleBinding
import com.example.hostelaccount.db.local.DbManager
import com.example.hostelaccount.db.local.PeopleItemModel
import com.example.hostelaccount.model.PeopleIdViewModel
import com.example.hostelaccount.view.FragmentManageHelper
import com.example.hostelaccount.viewmodel.ValidationInputData

class AddNewPeopleFragment : Fragment() {
    private lateinit var binding: FragmentAddNewPeopleBinding

    private lateinit var viewModel: PeopleIdViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {
        binding = FragmentAddNewPeopleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        viewModel.clearData()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // определение viewModel для приёма данных от фрагмента
        viewModel = ViewModelProvider(requireActivity())[PeopleIdViewModel::class.java]
        //  определение объекта viewModel и получение данных
        val inputData = viewModel.getData()

        // определение переменной БД
        val db = DbManager.getInstance(requireActivity())


        //  ЕСЛИ БЫЛИ ПЕРЕДАНЫ ДАННЫЕ ИЗ ДРУГОГО ФРАГМЕНТА, ТО ЗАПОЛНЯЕТ ПОЛЯ АВТОМАТИЧЕСКИ
        if (inputData != null) {
            fillFields(inputData)
            initDeleteBtn(db,inputData.id!!)
        }
        // слушатель нажатий на кнопку сохранить
        initSaveBtnListener(inputData, db)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddNewPeopleFragment()
    }

    // Функция для отображения короткого сообщения
    private fun showToast(msg: Int) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

    // инициализация кнопки сохранить
    private fun initSaveBtnListener(inputData: PeopleItemModel?, db: DbManager) {
        val validationInputData = ValidationInputData()
        var peopleItem: PeopleItemModel

        binding.btnSave.setOnClickListener {
            // ID будет присвоен только если передан объект PeopleItemModel
            val id: Int? = inputData?.id

            val name = binding.txtNameNewMan.text.toString().trim()
            val roomNum = binding.txtRoomNumNewMan.text.toString().trim()
            val dateFrom = binding.txtPlDateFrom.text.toString().trim()
            val dateTo = binding.txtPlDateTo.text.toString().trim()
            val usMan = binding.switchUsMan.isChecked
            val addInfo = binding.txtEdAddInfo.text.toString().trim()

            when {
                !validationInputData.validateNameStr(name) -> showToast(R.string.error_name_required)
                !validationInputData.validateDateStr(dateFrom) -> showToast(R.string.error_datefrom_required)
                !validationInputData.validateDateStr(dateTo) -> showToast(R.string.error_dateto_required)
                !validationInputData.validateIntNum(roomNum) -> showToast(R.string.error_roomnum_required)
                else -> {
                    // создание переменной с введёнными данными
                    peopleItem = PeopleItemModel(id, roomNum.toInt(), name, dateFrom, dateTo, usMan, addInfo)
                    // запуск нового потока для асинхронного сохранения данных в БД
                    Thread {
                        db.peopleDao().insertAll(peopleItem) // сохранение
                        // запуск первого фрагмента после сохранения
                        FragmentManageHelper(parentFragmentManager).initFragment(R.id.fragmentLayoutPeoples, ListRoomsFragment.newInstance())
                    }.start()
                }
            }
        }
    }


    // если были переданы данные - заполняет поля автоматически
    private fun fillFields(people: PeopleItemModel){
        binding.txtNameNewMan.setText(people.guestName)
        binding.txtRoomNumNewMan.setText(people.roomNumber.toString())
        binding.txtPlDateFrom.setText(people.liveFrom)
        binding.txtPlDateTo.setText(people.liveTo)
        binding.txtEdAddInfo.setText(people.addInfo)
        binding.switchUsMan.isChecked = people.usPeople
    }


    // инициализация кнопки удалить, и слушателя нажатий
    // когда слушатель срабатывает - отправляет команду в ДАО БД на удаление, и передаёт id удаляемого
    private fun initDeleteBtn(db: DbManager, id: Int) {
        binding.btnDelete.visibility = View.VISIBLE
        binding.btnDelete.setOnClickListener {
            Thread {
                db.peopleDao().deleteById(id)
                FragmentManageHelper(parentFragmentManager).initFragment(R.id.fragmentLayoutPeoples, ListRoomsFragment.newInstance())
            }.start()
        }
    }
}