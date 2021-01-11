package com.example.shared.data.mappers.event

import androidx.lifecycle.MutableLiveData
import com.example.shared.data.db.events.entity.EventToday
import com.example.shared.data.db.events.entity.ScheduledTime
import com.example.shared.data.entity.event.EventScheduledTimeUi
import com.example.shared.data.entity.event.EventTodayUi
import javax.inject.Inject

class ScheduledTimeUiFromScheduledTimeDbMapper @Inject constructor() {

    fun map (from : ScheduledTime):EventScheduledTimeUi{
        return EventScheduledTimeUi(
            id = from.eventId,
            timeSchedule = MutableLiveData(from.scheduledTime)
        )
    }
}