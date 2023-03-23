package com.example.hostelaccount.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.RecViewRoomListLayoutBinding
import com.example.hostelaccount.model.RoomModel
import com.example.hostelaccount.model.SharedViewModel

class RoomListAdapter(private val viewModel: SharedViewModel): RecyclerView.Adapter<RoomListAdapter.ViewHolder>() {
    inner class ViewHolder (val binding: RecViewRoomListLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    private var roomsList = ArrayList<RoomModel>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecViewRoomListLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return roomsList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtRoomNum.text = roomsList[position].roomNum.toString()

        holder.binding.txtManOnBed1.text = roomsList[position].nameMan1
        holder.binding.txtFromBed1.text = roomsList[position].liveFromMan1
        holder.binding.txtToBed1.text = roomsList[position].liveToMan1
        if (!roomsList[position].usMan1 && roomsList[position].nameMan1 != "") holder.binding.bed1.setBackgroundColor((holder.itemView.context as AppCompatActivity).getColor(
            R.color.not_us_man))

        holder.binding.txtManOnBed2.text = roomsList[position].nameMan2
        holder.binding.txtFromBed2.text = roomsList[position].liveFromMan2
        holder.binding.txtToBed2.text = roomsList[position].liveToMan2
        if (!roomsList[position].usMan2 && roomsList[position].nameMan2 != "") holder.binding.bed2.setBackgroundColor((holder.itemView.context as AppCompatActivity).getColor(
            R.color.not_us_man))

        holder.binding.txtManOnBed3.text = roomsList[position].nameMan3
        holder.binding.txtFromBed3.text = roomsList[position].liveFromMan3
        holder.binding.txtToBed3.text = roomsList[position].liveToMan3
        if (!roomsList[position].usMan3 && roomsList[position].nameMan3 != "") holder.binding.bed3.setBackgroundColor((holder.itemView.context as AppCompatActivity).getColor(
            R.color.not_us_man))

        holder.binding.txtManOnBed4.text = roomsList[position].nameMan4
        holder.binding.txtFromBed4.text = roomsList[position].liveFromMan4
        holder.binding.txtToBed4.text = roomsList[position].liveToMan4
        if (!roomsList[position].usMan4 && roomsList[position].nameMan4 != "") holder.binding.bed4.setBackgroundColor((holder.itemView.context as AppCompatActivity).getColor(
            R.color.not_us_man))


    /*  if (accountingList[position].profit) holder.binding.mainLinLayout.setBackgroundColor((holder.itemView.context as AppCompatActivity).getColor(
            R.color.profit_plus))

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
         }*/

    }

    fun setList(list: ArrayList<RoomModel>) {
        roomsList.addAll(list)
        notifyDataSetChanged()
    }
}