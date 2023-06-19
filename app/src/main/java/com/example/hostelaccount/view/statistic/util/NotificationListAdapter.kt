package com.example.hostelaccount.view.statistic.util

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelaccount.databinding.RecViewNotificationListLayoutBinding
import com.example.hostelaccount.data.data_sourse.PeopleItemModel
import com.example.hostelaccount.viewmodel.util.ProcessingDate


class NotificationListAdapter : RecyclerView.Adapter<NotificationListAdapter.ViewHolder>() {
    inner class ViewHolder ( val binding: RecViewNotificationListLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    private var peoplesList = ArrayList<PeopleItemModel>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecViewNotificationListLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun getItemCount(): Int {
        return peoplesList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtRoomNum.text = peoplesList[position].roomNumber.toString()
        holder.binding.txtManName.text = peoplesList[position].guestName
        holder.binding.txtDateTo.text = peoplesList[position].liveTo
        holder.binding.txtDelayDays.text = Math.abs(ProcessingDate().calculateDaysDifference(peoplesList[position].liveTo)).toString()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<PeopleItemModel>) {
        peoplesList.clear()
        peoplesList.addAll(list)
        notifyDataSetChanged()
    }
}