package com.example.hostelaccount.view.peoples

import android.os.Bundle
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
import com.example.hostelaccount.model.GetRoomsLiveDataModel
import com.example.hostelaccount.model.RoomModel
import com.example.hostelaccount.model.PeopleIdViewModel
import com.example.hostelaccount.view.FragmentManageHelper
import com.example.hostelaccount.viewmodel.CreatingRoomsArray

class ListRoomsFragment : Fragment() {

    lateinit var binding: FragmentListRoomsBinding

    private lateinit var adapter: RoomListAdapter
    private lateinit var recyclerView: RecyclerView

    private val getRoomsLiveData: GetRoomsLiveDataModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {
        binding = FragmentListRoomsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // запуск класса для обработки данных людей и преобразование их в массив комнат
        CreatingRoomsArray(requireContext()).getRoomList(getRoomsLiveData.roomsLiveData)
        // наблюдатель. Ждёт обработанные данные из класса CreatingRoomsArray
        getRoomsLiveData.roomsLiveData.observe(viewLifecycleOwner) {
            // когда получит данные - отправляет их в адаптер RecView
            initRecyclerView(it)
        }
        // инициализация кнопки добавления нового человека
        initAddButton()
    }



    companion object {
        @JvmStatic
        fun newInstance() = ListRoomsFragment()
    }

    // функция инициализации адаптера
    // Принимает массив комнат с уже распределёнными людьми
    private fun initRecyclerView(list:ArrayList<RoomModel>) {
        recyclerView = binding.recViewRoomsList
        val viewModel = ViewModelProvider(requireActivity()).get(PeopleIdViewModel::class.java)
        adapter = RoomListAdapter(viewModel)
        recyclerView.adapter = adapter
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