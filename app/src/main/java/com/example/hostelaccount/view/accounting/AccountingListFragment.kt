package com.example.hostelaccount.view.accounting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelaccount.R
import com.example.hostelaccount.view.accounting.util.AccountingListAdapter
import com.example.hostelaccount.databinding.FragmentAccountingListBinding
import com.example.hostelaccount.data.data_sourse.AccountingItemModel
import com.example.hostelaccount.data.repository.AccountingRepositoryImpl
import com.example.hostelaccount.viewmodel.accounting.AccountingEvent
import com.example.hostelaccount.viewmodel.accounting.AccountingViewModel
import com.example.hostelaccount.viewmodel.util.FragmentManageHelper

class AccountingListFragment : Fragment() {
    private lateinit var binding: FragmentAccountingListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AccountingViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {
        binding = FragmentAccountingListBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[AccountingViewModel::class.java]
        val repositoryImpl = AccountingRepositoryImpl(this.requireContext())
        viewModel.init(repositoryImpl)
        val adapter = AccountingListAdapter(viewModel)
        initRecyclerView(adapter)
        viewModel.state.observe(viewLifecycleOwner) {
            if (it.listAccountingItems != null) {
                updateRecView(it.listAccountingItems, adapter)
            }
        }
        initAddButton()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onEvent(AccountingEvent.GetAccountingItems)
    }


    companion object {
        @JvmStatic
        fun newInstance() = AccountingListFragment()
    }


    private fun initRecyclerView(adapter: AccountingListAdapter) {
        recyclerView = binding.recViewAccountingList
        recyclerView.adapter = adapter
    }


    private fun updateRecView(list:List<AccountingItemModel>, adapter: AccountingListAdapter){
        adapter.setList(list)
    }


    private fun initAddButton(){
        binding.btnAddNewEntry.setOnClickListener {
            FragmentManageHelper(parentFragmentManager)
                .initFragment(R.id.fragmentLayoutAccounting, AccountingAddNewEntryFragment.newInstance())
        }
    }
}