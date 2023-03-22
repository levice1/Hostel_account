package com.example.hostelaccount.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.RecViewAccountingListLayoutBinding
import com.example.hostelaccount.databinding.RecViewRoomListLayoutBinding
import com.example.hostelaccount.db.AccountingItemModel
import com.example.hostelaccount.model.SharedViewModel
import com.example.hostelaccount.view.accounting.AccountingAddNewEntryFragment
import com.example.hostelaccount.view.accounting.AccountingListFragment


class AccountingListAdapter(private val viewModel: SharedViewModel): RecyclerView.Adapter<AccountingListAdapter.ViewHolder>() {
    inner class ViewHolder ( val binding: RecViewAccountingListLayoutBinding) : RecyclerView.ViewHolder(binding.root)

   private var accountingList = ArrayList<AccountingItemModel>()


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

        if (accountingList[position].profit) holder.binding.mainLinLayout.setBackgroundColor((holder.itemView.context as AppCompatActivity).getColor(R.color.profit_plus))

        // слушатель нажатий на каждый елемент
        holder.itemView.setOnClickListener {
            // при нажатии устанавлявает данные у viewModel
            viewModel.setData(accountingList[position])
            // и запускает новый фрагмент
            val fragmentManager = (holder.itemView.context as AppCompatActivity).supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentLayoutAccounting, AccountingListFragment.newInstance()).apply {
                replace(R.id.fragmentLayoutAccounting, AccountingAddNewEntryFragment.newInstance())
                addToBackStack(null)
                commit()
            }
        }

    }

    fun setList(list: List<AccountingItemModel>) {
        accountingList.addAll(list)
        notifyDataSetChanged()
    }
}