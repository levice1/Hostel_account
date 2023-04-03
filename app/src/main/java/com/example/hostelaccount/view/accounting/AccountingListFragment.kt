package com.example.hostelaccount.view.accounting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelaccount.R
import com.example.hostelaccount.adapter.AccountingListAdapter
import com.example.hostelaccount.databinding.FragmentAccountingListBinding
import com.example.hostelaccount.db.local.AccountingItemModel
import com.example.hostelaccount.db.local.DbManager
import com.example.hostelaccount.model.AccountingViewModel
import com.example.hostelaccount.view.FragmentManageHelper
import com.example.hostelaccount.viewmodel.SortAccountingItems

class AccountingListFragment : Fragment() {
    private lateinit var binding: FragmentAccountingListBinding

    private lateinit var adapter: AccountingListAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {
        binding = FragmentAccountingListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // получение всех данных из БД и передача их в адаптер RecView
        DbManager.getInstance(requireActivity())
            .accountingDao()
            .getAll()
            .asLiveData()
            .observe(viewLifecycleOwner){
                initRecyclerView(it) // передача в адаптер
            }
        initAddButton() // инициализация кнопки добавления новой записи
    }

    companion object {
        @JvmStatic
        fun newInstance() = AccountingListFragment()
    }

    private fun initRecyclerView(list:List<AccountingItemModel>) { // функция инициализации адаптера
        recyclerView = binding.recViewAccountingList
        val viewModel = ViewModelProvider(requireActivity()).get(AccountingViewModel::class.java)
        adapter = AccountingListAdapter(viewModel)
        recyclerView.adapter = adapter
        adapter.setList(SortAccountingItems(list).getSortedList())
    }

    private fun initAddButton(){ // функция инициализации кнопки добавления новой записи
        binding.btnAddNewEntry.setOnClickListener {
            FragmentManageHelper(parentFragmentManager)
                .initFragment(R.id.fragmentLayoutAccounting, AccountingAddNewEntryFragment.newInstance())
        }
    }
}