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
import com.example.hostelaccount.data.data_sourse.PeopleItemModel
import com.example.hostelaccount.viewmodel.peoples.PeoplesEvent
import com.example.hostelaccount.viewmodel.peoples.PeoplesViewModel
import com.example.hostelaccount.viewmodel.util.FragmentManageHelper
import com.example.hostelaccount.viewmodel.util.ValidationInputData

class AddNewPeopleFragment : Fragment() {

    private lateinit var binding: FragmentAddNewPeopleBinding

    private lateinit var viewModel: PeoplesViewModel

    private var tempSavedResident: PeopleItemModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {
        binding = FragmentAddNewPeopleBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // определение viewModel для приёма данных от фрагмента
        viewModel = ViewModelProvider(requireActivity())[PeoplesViewModel::class.java]
        //  определение объекта viewModel и получение данных
        viewModel.onEvent(PeoplesEvent.GetTempResident)
        //  ЕСЛИ БЫЛИ ПЕРЕДАНЫ ДАННЫЕ ИЗ ДРУГОГО ФРАГМЕНТА, ТО ЗАПОЛНЯЕТ ПОЛЯ АВТОМАТИЧЕСКИ
        viewModel.state.observe(viewLifecycleOwner){
            if (it.tempResidentItem != null) {
                tempSavedResident = it.tempResidentItem
                fillFields(tempSavedResident!!)
                initDeleteBtn(it.tempResidentItem)
            }
        }
        // слушатель нажатий на кнопку сохранить
        initSaveBtnListener()
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
    private fun initSaveBtnListener() {
        val validInptData = ValidationInputData()
        binding.btnSave.setOnClickListener {
            // ЕСЛИ БЫЛИ ПЕРЕДАНЫ ДАННЫЕ, ТО ID ПРИСВАЕВАЕТ ТОТ ЧТО БЫЛ ПЕРЕДАН. ЕСЛИ НЕТ ТО NULL
            val id: Int? = tempSavedResident?.id
            val name = binding.txtNameNewMan.text.toString().trim()
            val roomNum = binding.txtRoomNumNewMan.text.toString().trim()
            val dateFrom = binding.txtPlDateFrom.text.toString().trim()
            val dateTo = binding.txtPlDateTo.text.toString().trim()
            val usMan = binding.switchUsMan.isChecked
            val addInfo = binding.txtEdAddInfo.text.toString().trim()

            when {
                !validInptData.validateNameStr(name) -> showToast(R.string.error_name_required)
                !validInptData.validateDateStr(dateFrom) -> showToast(R.string.error_datefrom_required)
                !validInptData.validateDateStr(dateTo) -> showToast(R.string.error_dateto_required)
                !validInptData.validateIntNum(roomNum) -> showToast(R.string.error_roomnum_required)
                else -> {
                    // создание переменной с введёнными данными
                   val residentItem = PeopleItemModel(id, roomNum.toInt(), name, dateFrom, dateTo, usMan, addInfo)
                    // запуск нового потока для асинхронного сохранения данных в БД
                        viewModel.onEvent(PeoplesEvent.SaveResident(residentItem))
                    // запуск первого фрагмента после сохранения
                    FragmentManageHelper(parentFragmentManager).initFragment(R.id.fragmentLayoutPeoples, ListRoomsFragment.newInstance())
                }
            }
        }
    }


    // инициализация кнопки удалить, и слушателя нажатий
    // когда слушатель срабатывает - отправляет команду в viewModel на удаление, и передаёт id удаляемого
    private fun initDeleteBtn(resident: PeopleItemModel) {
        binding.btnDelete.visibility = View.VISIBLE
        binding.btnDelete.setOnClickListener {
                viewModel.onEvent(PeoplesEvent.DeleteResident(resident))
            FragmentManageHelper(parentFragmentManager).initFragment(R.id.fragmentLayoutPeoples, ListRoomsFragment.newInstance())
        }
    }
    // функция для заполнения полей переданными данными
    private fun fillFields(resident: PeopleItemModel){
        binding.txtNameNewMan.setText(resident.guestName)
        binding.txtRoomNumNewMan.setText(resident.roomNumber.toString())
        binding.txtPlDateFrom.setText(resident.liveFrom)
        binding.txtPlDateTo.setText(resident.liveTo)
        binding.txtEdAddInfo.setText(resident.addInfo)
        binding.switchUsMan.isChecked = resident.usPeople
    }
}