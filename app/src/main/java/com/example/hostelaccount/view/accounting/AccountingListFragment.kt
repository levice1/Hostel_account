package com.example.hostelaccount.view.accounting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelaccount.R
import com.example.hostelaccount.adapter.AccountingListAdapter
import com.example.hostelaccount.databinding.ActivityAccountingBinding
import com.example.hostelaccount.databinding.FragmentAccountingListBinding
import com.example.hostelaccount.db.DbManager
import com.example.hostelaccount.model.AccountingListModel

class AccountingListFragment : Fragment() {
    lateinit var binding: FragmentAccountingListBinding

    private lateinit var adapter: AccountingListAdapter
    private lateinit var recyclerView: RecyclerView

    private var accountingList = ArrayList<AccountingListModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = DbManager.getDb(this@AccountingListFragment)
        db.getDao().getAllItem().asLiveData().observe(this){
         initRecyclerView(it)
        }

        binding.btnAddNewEntry.setOnClickListener(){
TODO() переключення одного фрагменту з другого
            val bind: AccountingActivity
            bind = AccountingActivity()
            fragmentManager?.beginTransaction()?.replace(   , AccountingAddNewEntryFragment, "TAG")?.commit();




        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {
        binding = FragmentAccountingListBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = AccountingListFragment()
    }

    private fun initRecyclerView(list:ArrayList<AccountingListModel>) {
        recyclerView = binding.recViewAccountingList
        adapter = AccountingListAdapter()
        recyclerView.adapter = adapter
        adapter.setList(list)

    }
}