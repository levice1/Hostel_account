package com.example.hostelaccount.view.peoples

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelaccount.R
import com.example.hostelaccount.adapter.RoomListAdapter
import com.example.hostelaccount.databinding.FragmentListRoomsBinding
import com.example.hostelaccount.db.local.DbManager
import com.example.hostelaccount.model.RoomModel
import com.example.hostelaccount.model.PeopleIdViewModel
import com.example.hostelaccount.viewmodel.FragmentManageHelper
import com.example.hostelaccount.viewmodel.CreatingPeoplesList

class ListRoomsFragment : Fragment() {

    private lateinit var binding: FragmentListRoomsBinding

    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {
        binding = FragmentListRoomsBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity())[PeopleIdViewModel::class.java]
        val adapter = RoomListAdapter(viewModel)
        // инициализация RecView
        initRecyclerView(adapter)
        // получение всех данных из БД и обновление адаптера RecView
        DbManager.getInstance(requireActivity())
            .peopleDao()
            .getAll()
            .asLiveData()
            .observe(viewLifecycleOwner){
                val roomsArray = CreatingPeoplesList().createRoomList(it) // получение массива комнат
                updateRecView(roomsArray, adapter)// передача в адаптер
            }
        // инициализация кнопки добавления нового человека
        initAddButton()
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
    private fun updateRecView(list:ArrayList<RoomModel>, adapter: RoomListAdapter){
        adapter.setList(list)
    }


    // функция инициализации кнопки добавления нового человека
    private fun initAddButton(){
        binding.btnAddNewGuest.setOnClickListener {
            FragmentManageHelper(parentFragmentManager)
                .initFragment(R.id.fragmentLayoutPeoples, AddNewPeopleFragment.newInstance())
        }
    }
}