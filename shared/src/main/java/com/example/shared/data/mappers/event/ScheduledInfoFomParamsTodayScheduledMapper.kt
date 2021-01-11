package com.example.shared.data.mappers.event

import com.example.shared.data.db.events.entity.EventParams
import com.example.shared.data.db.events.entity.EventToday
import com.example.shared.data.db.events.entity.ScheduledTime
import com.example.shared.data.entity.event.EventScheduledInfoUi
import javax.inject.Inject

class ScheduledInfoFromParamsTodayScheduledMapper @Inject constructor() {

    fun map(spentTime: Int, fromP: EventParams, fromS: ScheduledTime): EventScheduledInfoUi {
        return EventScheduledInfoUi(
            id = fromP.eventId,
            dbId = fromS.idDb ?: -1,
            name = fromP.eventName,
            color = fromP.eventColor,
            scheduledTime = fromS.scheduledTime,
            spentTime = spentTime
        )
    }
}