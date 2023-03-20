package com.example.hostelaccount.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelaccount.databinding.RecViewAccountingListLayoutBinding
import com.example.hostelaccount.model.AccountingListModel

class AccountingListAdapter: RecyclerView.Adapter<AccountingListAdapter.ViewHolder>() {
    inner class ViewHolder ( val binding: RecViewAccountingListLayoutBinding) : RecyclerView.ViewHolder(binding.root)

   private var accountingList = ArrayList<AccountingListModel>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecViewAccountingListLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return accountingList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtDateItem.text = accountingList[position].date
        holder.binding.txtReasonItem.text = accountingList[position].reason
        holder.binding.txtSumItem.text = accountingList[position].sum.toString()
        if (accountingList[position].profit) holder.binding.mainLinLayout.setBackgroundColor(1007617580)
    }

    fun setList(list: List<AccountingListModel>) {
        accountingList.addAll(list)
        notifyDataSetChanged()
    }
}