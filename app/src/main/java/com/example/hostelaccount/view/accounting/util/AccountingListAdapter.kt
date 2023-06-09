package com.example.hostelaccount.view.accounting.util

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.RecViewAccountingListLayoutBinding
import com.example.hostelaccount.data.data_sourse.AccountingItemModel
import com.example.hostelaccount.viewmodel.accounting.AccountingViewModel
import com.example.hostelaccount.viewmodel.util.FragmentManageHelper
import com.example.hostelaccount.view.accounting.AccountingAddNewEntryFragment
import com.example.hostelaccount.view.accounting.AccountingListFragment
import com.example.hostelaccount.viewmodel.accounting.AccountingEvent


class AccountingListAdapter(private val viewModel: AccountingViewModel) :
    RecyclerView.Adapter<AccountingListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RecViewAccountingListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var accountingList = ArrayList<AccountingItemModel>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecViewAccountingListLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return accountingList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtDateItem.text = accountingList[position].date
        holder.binding.txtReasonItem.text = accountingList[position].reason
        holder.binding.txtSumItem.text = accountingList[position].sum.toString()

        if (accountingList[position].profit) holder.binding.mainLinLayout
            .setBackgroundColor(
                (holder.itemView.context as AppCompatActivity)
                    .getColor(R.color.profit_plus)
            )
        else {
            holder.binding.mainLinLayout
                .setBackgroundColor(
                    (holder.itemView.context as AppCompatActivity)
                        .getColor(R.color.profit_minus)
                )
        }

        holder.itemView.setOnClickListener {
            // save to viewModel temporary variable
            viewModel.onEvent(AccountingEvent.SaveTempItem(accountingList[position]))
            // run new fragment
            val fragmentManager =
                (holder.itemView.context as AppCompatActivity).supportFragmentManager
            FragmentManageHelper(fragmentManager)
                .replaceFragment(
                    R.id.fragmentLayoutAccounting,
                    AccountingListFragment.newInstance(),
                    AccountingAddNewEntryFragment.newInstance()
                )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<AccountingItemModel>) {
        accountingList.clear()
        accountingList.addAll(list)
        notifyDataSetChanged()
    }
}