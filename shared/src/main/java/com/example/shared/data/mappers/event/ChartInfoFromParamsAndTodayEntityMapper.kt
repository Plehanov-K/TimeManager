package com.example.shared.data.mappers.event

import com.example.shared.data.db.events.entity.EventParams
import com.example.shared.data.db.events.entity.EventToday
import com.example.shared.data.entity.event.EventChartInfoUi
import com.example.shared.data.entity.event.EventInfoUi
import javax.inject.Inject

class ChartInfoFromParamsAndTodayEntityMapper @Inject constructor() {

    fun map(spentTime:Int, fromP: EventParams): EventChartInfoUi {
        return EventChartInfoUi(
            id = fromP.eventId,
            name = fromP.eventName,
            color = fromP.eventColor,
            spentTime = spentTime
        )
    }
}