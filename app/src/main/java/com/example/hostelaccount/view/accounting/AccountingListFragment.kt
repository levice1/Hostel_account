package com.example.hostelaccount.view.accounting

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelaccount.R
import com.example.hostelaccount.adapter.AccountingListAdapter
import com.example.hostelaccount.databinding.FragmentAccountingListBinding
import com.example.hostelaccount.db.DbManager
import com.example.hostelaccount.model.AccountingListModel

@Suppress("DEPRECATION")
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

        val db = DbManager.getInstance(requireActivity())
        db.accountingDao().getAll().asLiveData().observe(viewLifecycleOwner){
            initRecyclerView(it)
        }
        initAddButton()
    }





    companion object {
        @JvmStatic
        fun newInstance() = AccountingListFragment()
    }

    private fun initRecyclerView(list:List<AccountingListModel>) {
        recyclerView = binding.recViewAccountingList
        adapter = AccountingListAdapter()
        recyclerView.adapter = adapter
        adapter.setList(list)

    }

    private fun initAddButton(){
        binding.btnAddNewEntry.setOnClickListener {
            // переключення одного фрагменту з другого
            val fragmentManager = fragmentManager
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentLayoutAccounting, AccountingAddNewEntryFragment.newInstance(), "TAG")!!
                .commit()
        }
    }
}