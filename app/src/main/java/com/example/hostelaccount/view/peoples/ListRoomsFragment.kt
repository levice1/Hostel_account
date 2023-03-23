package com.example.hostelaccount.view.peoples

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelaccount.R
import com.example.hostelaccount.adapter.RoomListAdapter
import com.example.hostelaccount.databinding.FragmentListRoomsBinding
import com.example.hostelaccount.db.DbManager
import com.example.hostelaccount.model.GetRoomsLiveDataModel
import com.example.hostelaccount.model.RoomModel
import com.example.hostelaccount.model.SharedViewModel
import com.example.hostelaccount.viewmodel.ProcessingPeoplesData

class ListRoomsFragment : Fragment() {
    lateinit var binding: FragmentListRoomsBinding

    private lateinit var adapter: RoomListAdapter
    private lateinit var recyclerView: RecyclerView

    val roomListLiveData: GetRoomsLiveDataModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {
        binding = FragmentListRoomsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAddButton()


    val process = ProcessingPeoplesData(requireContext())
    process.getRoomList(roomListLiveData.dbResponse)
    roomListLiveData.dbResponse.observe(viewLifecycleOwner) {
            Log.d("TestMsg", "RoomList: $it")
            initRecyclerView(it)
    }

    }

    companion object {
        @JvmStatic
        fun newInstance() = ListRoomsFragment()
    }

    private fun initRecyclerView(list:ArrayList<RoomModel>) { // функция инициализации адаптера
        recyclerView = binding.recViewRoomsList
        val viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        adapter = RoomListAdapter(viewModel)
        recyclerView.adapter = adapter
        adapter.setList(list)
    }

    private fun initAddButton(){ // функция инициализации кнопки добавления новой записи
        binding.btnAddNewGuest.setOnClickListener {
            val fragmentManager = fragmentManager
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentLayoutPeoples, AddNewPeopleFragment.newInstance(), "TAG")!!
                .commit()
        }
    }
}