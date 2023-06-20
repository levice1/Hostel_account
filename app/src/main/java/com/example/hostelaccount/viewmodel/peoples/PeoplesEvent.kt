package com.example.hostelaccount.viewmodel.peoples

import com.example.hostelaccount.data.data_sourse.PeopleItemModel

sealed class PeoplesEvent {
    data class SaveTempResident(val tempResident: PeopleItemModel) : PeoplesEvent()
    data class DeleteResident(val resident: PeopleItemModel) : PeoplesEvent()
    data class SaveResident(val resident: PeopleItemModel) : PeoplesEvent()
    object GetTempResident : PeoplesEvent()
    object GetRoomsList : PeoplesEvent()
}
