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
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewPeopleBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[PeoplesViewModel::class.java]
        viewModel.onEvent(PeoplesEvent.GetTempResident)
        // if data were transferred via a viewModel, it fills in the fields automatically
        // and do Delete button active
        viewModel.state.observe(viewLifecycleOwner) {
            if (it.tempResidentItem != null) {
                tempSavedResident = it.tempResidentItem
                fillFields(tempSavedResident!!)
                initDeleteBtn(it.tempResidentItem)
            }
        }
        initSaveBtnListener()
    }


    companion object {
        @JvmStatic
        fun newInstance() = AddNewPeopleFragment()
    }


    private fun showToast(msg: Int) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }


    private fun initSaveBtnListener() {
        val validInputData = ValidationInputData()
        binding.btnSave.setOnClickListener {
            // if the data was transmitted, it assigns an ID to the one that was transmitted
            // if not, it assigns null
            val id: Int? = tempSavedResident?.id
            val name = binding.txtNameNewMan.text.toString().trim()
            val roomNum = binding.txtRoomNumNewMan.text.toString().trim()
            val dateFrom = binding.txtPlDateFrom.text.toString().trim()
            val dateTo = binding.txtPlDateTo.text.toString().trim()
            val usMan = binding.switchUsMan.isChecked
            val addInfo = binding.txtEdAddInfo.text.toString().trim()

            when {
                !validInputData.validateNameStr(name) -> showToast(R.string.error_name_required)
                !validInputData.validateDateStr(dateFrom) -> showToast(R.string.error_datefrom_required)
                !validInputData.validateDateStr(dateTo) -> showToast(R.string.error_dateto_required)
                !validInputData.validateIntNum(roomNum) -> showToast(R.string.error_roomnum_required)
                else -> {
                    // create a variable with the entered data
                    val residentItem =
                        PeopleItemModel(id, roomNum.toInt(), name, dateFrom, dateTo, usMan, addInfo)

                    viewModel.onEvent(PeoplesEvent.SaveResident(residentItem))

                    FragmentManageHelper(parentFragmentManager).initFragment(
                        R.id.fragmentLayoutPeoples,
                        ListRoomsFragment.newInstance()
                    )
                }
            }
        }
    }


    private fun initDeleteBtn(resident: PeopleItemModel) {
        binding.btnDelete.visibility = View.VISIBLE
        binding.btnDelete.setOnClickListener {

            viewModel.onEvent(PeoplesEvent.DeleteResident(resident))

            FragmentManageHelper(parentFragmentManager).initFragment(
                R.id.fragmentLayoutPeoples,
                ListRoomsFragment.newInstance()
            )
        }
    }


    private fun fillFields(resident: PeopleItemModel) {
        binding.txtNameNewMan.setText(resident.guestName)
        binding.txtRoomNumNewMan.setText(resident.roomNumber.toString())
        binding.txtPlDateFrom.setText(resident.liveFrom)
        binding.txtPlDateTo.setText(resident.liveTo)
        binding.txtEdAddInfo.setText(resident.addInfo)
        binding.switchUsMan.isChecked = resident.usPeople
    }
}