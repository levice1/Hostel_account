package com.example.hostelaccount.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelaccount.databinding.RecViewNotificationListLayoutBinding
import com.example.hostelaccount.db.local.PeopleItemModel
import com.example.hostelaccount.viewmodel.ProcessingDate


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
        holder.binding.txtDelayDays.text = ProcessingDate().calculateDaysDifference(peoplesList[position].liveTo)!!.toString()
    }

    fun setList(list: List<PeopleItemModel>) {
        peoplesList.addAll(list)
        notifyDataSetChanged()
    }
}