package com.example.shared.data.mappers.event

import androidx.lifecycle.MutableLiveData
import com.example.shared.data.db.events.entity.EventToday
import com.example.shared.data.entity.event.EventTodayUi
import javax.inject.Inject

class EventTodayFromEventDbMapper @Inject constructor() {

    fun map (from : EventToday):EventTodayUi{
        return EventTodayUi(
            id = from.eventId,
            timeSpent = MutableLiveData(from.time),
        )
    }
}