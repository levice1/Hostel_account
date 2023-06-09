package com.example.hostelaccount.view.peoples.util


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelaccount.R
import com.example.hostelaccount.data.data_sourse.PeopleItemModel
import com.example.hostelaccount.databinding.RecViewRoomListLayoutBinding
import com.example.hostelaccount.model.RoomModel
import com.example.hostelaccount.viewmodel.peoples.PeoplesViewModel
import com.example.hostelaccount.viewmodel.util.FragmentManageHelper
import com.example.hostelaccount.view.peoples.AddNewPeopleFragment
import com.example.hostelaccount.view.peoples.ListRoomsFragment
import com.example.hostelaccount.viewmodel.peoples.PeoplesEvent
import com.example.hostelaccount.viewmodel.util.GetDateTime

class RoomListAdapter(private val viewModel: PeoplesViewModel) :
    RecyclerView.Adapter<RoomListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RecViewRoomListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var roomsList = ArrayList<RoomModel>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecViewRoomListLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun getItemCount(): Int {
        return roomsList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // reset field visibility before assigning new values
        // bug fix with disappearance of some fields in the elements
        repareVisibility(holder)


        // room number
        holder.binding.txtRoomNum.text = roomsList[position].roomNum.toString()

        // --------------------------------------------------------------------------------------- //
        // BED 1
        holder.binding.txtManOnBed1.text = roomsList[position].people[0].guestName
        holder.binding.txtFromBed1.text = roomsList[position].people[0].liveFrom
        holder.binding.txtToBed1.text = roomsList[position].people[0].liveTo
        // checks to see if the person is ours or not. If not, it shades his field a different color
        if (!roomsList[position].people[0].usPeople) {
            holder.binding.bed1.setBackgroundResource(R.drawable.background_element_not_us_people)
            // setting the color of the date depending on the number of days until the end of the lease
            setTextColorBasedOnDate(
                roomsList[position].people[0].liveTo,
                holder.binding.txtToBed1,
                holder.itemView.context
            )
        } else {
            // if the person is ours, leaves the color of his field transparent
            holder.binding.bed1.setBackgroundResource(R.drawable.background_elements_same_transparent)
        }
        holder.binding.bed1.setOnClickListener {
            startFragForEditing(holder, roomsList[position].people[0])
        }

        // --------------------------------------------------------------------------------------- //
        // BED 2
        holder.binding.txtManOnBed2.text = roomsList[position].people[1].guestName
        holder.binding.txtFromBed2.text = roomsList[position].people[1].liveFrom
        holder.binding.txtToBed2.text = roomsList[position].people[1].liveTo
        if (!roomsList[position].people[1].usPeople) {
            holder.binding.bed2.setBackgroundResource(R.drawable.background_element_not_us_people)
            setTextColorBasedOnDate(
                roomsList[position].people[1].liveTo,
                holder.binding.txtToBed2,
                holder.itemView.context
            )
        } else {
            holder.binding.bed2.setBackgroundResource(R.drawable.background_elements_same_transparent)
        }
        // checks to see if the data is on this bed. If not, it hides the field
        if (roomsList[position].people[1].id == 0) {
            holder.binding.bed2.visibility = View.GONE
        }
        holder.binding.bed2.setOnClickListener {
            startFragForEditing(
                holder,
                roomsList[position].people[1]
            )
        }

        // --------------------------------------------------------------------------------------- //
        // BED 3
        holder.binding.txtManOnBed3.text = roomsList[position].people[2].guestName
        holder.binding.txtFromBed3.text = roomsList[position].people[2].liveFrom
        holder.binding.txtToBed3.text = roomsList[position].people[2].liveTo
        if (!roomsList[position].people[2].usPeople) {
            holder.binding.bed3.setBackgroundResource(R.drawable.background_element_not_us_people)
            setTextColorBasedOnDate(
                roomsList[position].people[2].liveTo,
                holder.binding.txtToBed3,
                holder.itemView.context
            )
        } else {
            holder.binding.bed3.setBackgroundResource(R.drawable.background_elements_same_transparent)
        }
        if (roomsList[position].people[2].id == 0 && roomsList[position].people[2].guestName == "") {
            holder.binding.bed3.visibility = View.GONE
        }
        holder.binding.bed3.setOnClickListener {
            startFragForEditing(
                holder,
                roomsList[position].people[2]
            )
        }

        // --------------------------------------------------------------------------------------- //
        // BED 4
        holder.binding.txtManOnBed4.text = roomsList[position].people[3].guestName
        holder.binding.txtFromBed4.text = roomsList[position].people[3].liveFrom
        holder.binding.txtToBed4.text = roomsList[position].people[3].liveTo
        if (!roomsList[position].people[3].usPeople && roomsList[position].people[3].guestName != "") {
            holder.binding.bed4.setBackgroundResource(R.drawable.background_element_not_us_people)
            setTextColorBasedOnDate(
                roomsList[position].people[3].liveTo,
                holder.binding.txtToBed4,
                holder.itemView.context
            )
        } else {
            holder.binding.bed4.setBackgroundResource(R.drawable.background_elements_same_transparent)
        }
        if (roomsList[position].people[3].id == 0 && roomsList[position].people[3].guestName == "") {
            holder.binding.bed4.visibility = View.GONE
        }
        holder.binding.bed4.setOnClickListener {
            startFragForEditing(
                holder,
                roomsList[position].people[3]
            )
        }
    }


    private fun startFragForEditing(holder: ViewHolder, resident: PeopleItemModel) {
        viewModel.onEvent(PeoplesEvent.SaveTempResident(resident))
        // and starts a new fragment
        FragmentManageHelper((holder.itemView.context as AppCompatActivity).supportFragmentManager)
            .replaceFragment(
                R.id.fragmentLayoutPeoples,
                ListRoomsFragment.newInstance(),
                AddNewPeopleFragment.newInstance()
            )
    }


    private fun setTextColorBasedOnDate(dateString: String, textView: TextView, context: Context) {
        when (GetDateTime().calculateDaysDifference(dateString)) {
            in -999..0 -> textView.setTextColor(context.getColor(R.color.overdue_payment))
            in 1..3 -> textView.setTextColor(context.getColor(R.color.soon_overgue))
            else -> textView.setTextColor(context.getColor(R.color.white))
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<RoomModel>) {
        roomsList.clear()
        roomsList.addAll(list)
        notifyDataSetChanged()
    }

    private fun repareVisibility(holder: ViewHolder) {
        // reset field visibility before assigning new values
        // bug fix with disappearance of some fields in the elements
        // bug with incorrect color depending on the date
        holder.binding.bed2.visibility = View.VISIBLE
        holder.binding.bed3.visibility = View.VISIBLE
        holder.binding.bed4.visibility = View.VISIBLE
        holder.binding.txtToBed1.setTextColor(
            (holder.itemView.context as AppCompatActivity).getColor(
                R.color.white
            )
        )
        holder.binding.txtToBed2.setTextColor(
            (holder.itemView.context as AppCompatActivity).getColor(
                R.color.white
            )
        )
        holder.binding.txtToBed3.setTextColor(
            (holder.itemView.context as AppCompatActivity).getColor(
                R.color.white
            )
        )
        holder.binding.txtToBed4.setTextColor(
            (holder.itemView.context as AppCompatActivity).getColor(
                R.color.white
            )
        )
    }
}
