package com.example.hostelaccount.view.peoples

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelaccount.R
import com.example.hostelaccount.data.repository.PeopleRepositoryImpl
import com.example.hostelaccount.view.peoples.util.RoomListAdapter
import com.example.hostelaccount.databinding.FragmentListRoomsBinding
import com.example.hostelaccount.model.RoomModel
import com.example.hostelaccount.viewmodel.peoples.PeoplesEvent
import com.example.hostelaccount.viewmodel.peoples.PeoplesViewModel
import com.example.hostelaccount.viewmodel.util.FragmentManageHelper

class ListRoomsFragment : Fragment() {

    private lateinit var binding: FragmentListRoomsBinding

    private lateinit var recyclerView: RecyclerView

    private lateinit var viewModel: PeoplesViewModel

    private lateinit var adapter: RoomListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {
        binding = FragmentListRoomsBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[PeoplesViewModel::class.java]
        adapter = RoomListAdapter(viewModel)
        val repository = PeopleRepositoryImpl(this.requireContext())

        initRecyclerView(adapter)
        viewModel.init(repository)

        viewModel.onEvent(PeoplesEvent.GetRoomsList)

        viewModel.state.observe(viewLifecycleOwner) {
            if (it.listRooms != null) {
                    updateRecView(it.listRooms, adapter)// передача в адаптер
                }
        }
        initAddNewPeopleButton()
    }


    companion object {
        @JvmStatic
        fun newInstance() = ListRoomsFragment()
    }


    // функция инициализации RecView
    private fun initRecyclerView(adapter: RoomListAdapter) {
        recyclerView = binding.recViewRoomsList
        recyclerView.adapter = adapter
    }


    // функция обновления адаптера RecView
    // Принимает массив комнат с уже распределёнными людьми
    private fun updateRecView(list:List<RoomModel>, adapter: RoomListAdapter){
        adapter.setList(list)
    }


    // функция инициализации кнопки добавления нового человека
    private fun initAddNewPeopleButton(){
        binding.btnAddNewGuest.setOnClickListener {
            FragmentManageHelper(parentFragmentManager)
                .initFragment(R.id.fragmentLayoutPeoples, AddNewPeopleFragment.newInstance())
        }
    }
}