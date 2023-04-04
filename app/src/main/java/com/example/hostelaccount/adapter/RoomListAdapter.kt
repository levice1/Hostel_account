package com.example.hostelaccount.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.RecViewRoomListLayoutBinding
import com.example.hostelaccount.model.RoomModel
import com.example.hostelaccount.model.PeopleIdViewModel
import com.example.hostelaccount.model.Resident
import com.example.hostelaccount.view.FragmentManageHelper
import com.example.hostelaccount.view.peoples.AddNewPeopleFragment
import com.example.hostelaccount.view.peoples.ListRoomsFragment

class RoomListAdapter(private val viewModel: PeopleIdViewModel): RecyclerView.Adapter<RoomListAdapter.ViewHolder>() {
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

        // BED 1
        holder.binding.txtManOnBed1.text = roomsList[position].people[0].name
        holder.binding.txtFromBed1.text = roomsList[position].people[0].liveFrom
        holder.binding.txtToBed1.text = roomsList[position].people[0].liveTo
        if (!roomsList[position].people[0].usMan && roomsList[position].people[0].name != ""){
            holder.binding.bed1.setBackgroundColor((holder.itemView.context as AppCompatActivity).getColor(R.color.not_us_man))
        } else {
            holder.binding.bed1.setBackgroundColor((holder.itemView.context as AppCompatActivity).getColor(R.color.transparent))
        }
        holder.binding.bed1.setOnClickListener(){
            startFragForEditing(holder,roomsList[position].roomNum ,roomsList[position].people[0])
        }

        // BED 2
        holder.binding.txtManOnBed2.text = roomsList[position].people[1].name
        holder.binding.txtFromBed2.text = roomsList[position].people[1].liveFrom
        holder.binding.txtToBed2.text = roomsList[position].people[1].liveTo
        if (!roomsList[position].people[1].usMan && roomsList[position].people[1].name != ""){
            holder.binding.bed2.setBackgroundColor((holder.itemView.context as AppCompatActivity).getColor(R.color.not_us_man))
        } else {
            holder.binding.bed2.setBackgroundColor((holder.itemView.context as AppCompatActivity).getColor(R.color.transparent))
        }
        holder.binding.bed2.setOnClickListener(){
            startFragForEditing(holder,roomsList[position].roomNum ,roomsList[position].people[1])
        }

        // BED 3
        holder.binding.txtManOnBed3.text = roomsList[position].people[2].name
        holder.binding.txtFromBed3.text = roomsList[position].people[2].liveFrom
        holder.binding.txtToBed3.text = roomsList[position].people[2].liveTo
        if (!roomsList[position].people[2].usMan && roomsList[position].people[2].name != "") {
            holder.binding.bed3.setBackgroundColor((holder.itemView.context as AppCompatActivity).getColor(R.color.not_us_man))
        } else {
            holder.binding.bed3.setBackgroundColor((holder.itemView.context as AppCompatActivity).getColor(R.color.transparent))
        }
        holder.binding.bed3.setOnClickListener(){
            startFragForEditing(holder,roomsList[position].roomNum ,roomsList[position].people[2])
        }

        // BED 4
        holder.binding.txtManOnBed4.text = roomsList[position].people[3].name
        holder.binding.txtFromBed4.text = roomsList[position].people[3].liveFrom
        holder.binding.txtToBed4.text = roomsList[position].people[3].liveTo
        if (!roomsList[position].people[3].usMan && roomsList[position].people[3].name != "") {
            holder.binding.bed4.setBackgroundColor((holder.itemView.context as AppCompatActivity).getColor(R.color.not_us_man))
        } else {
            holder.binding.bed4.setBackgroundColor((holder.itemView.context as AppCompatActivity).getColor(R.color.transparent))
        }
        holder.binding.bed4.setOnClickListener(){
            startFragForEditing(holder,roomsList[position].roomNum ,roomsList[position].people[3])
        }

    }

    private fun startFragForEditing(holder: ViewHolder, roomNum: Int, resident: Resident){
        viewModel.setData(resident, roomNum)
        // и запускает новый фрагмент
        val fragmentManager = (holder.itemView.context as AppCompatActivity).supportFragmentManager
        FragmentManageHelper(fragmentManager)
            .replaceFragment(
                R.id.fragmentLayoutPeoples,
                ListRoomsFragment.newInstance(),
                AddNewPeopleFragment.newInstance()
            )
    }

    fun setList(list: ArrayList<RoomModel>) {
        roomsList.addAll(list)
        notifyDataSetChanged()
    }
}
