package com.example.hostelaccount.view.accounting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.FragmentAccountingAddNewEntryBinding
import com.example.hostelaccount.db.Accounting
import com.example.hostelaccount.db.DbManager
import com.example.hostelaccount.model.AccountingListModel


class AccountingAddNewEntryFragment : Fragment() {
    lateinit var binding: FragmentAccountingAddNewEntryBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {
        binding = FragmentAccountingAddNewEntryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener {
            val accountingModel = Accounting(null,
                binding.txtPlDate.text.toString(),
                binding.txtPlWhoOrWhat.text.toString(),
                binding.txtPlSum.text.toString().toInt(),
                true // TODO(виправити перемикач плюс чи мінус)
            )
            val db = DbManager.getInstance(requireActivity())
            Thread{
                db.accountingDao().insertAll(accountingModel)
                val fragmentManager = fragmentManager
                fragmentManager?.beginTransaction()
                    ?.replace(
                        R.id.fragmentLayoutAccounting,
                        AccountingListFragment.newInstance(),
                        "TAG"
                    )!!
                    .commit()
            }.start()

        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = AccountingAddNewEntryFragment()
    }

}


