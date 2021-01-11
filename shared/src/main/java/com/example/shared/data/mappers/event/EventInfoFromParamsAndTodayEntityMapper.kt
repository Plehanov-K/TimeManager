package com.example.shared.data.mappers.event

import com.example.shared.data.db.events.entity.EventParams
import com.example.shared.data.db.events.entity.EventToday
import com.example.shared.data.entity.event.EventInfoUi
import javax.inject.Inject

class EventInfoFromParamsAndTodayEntityMapper @Inject constructor() {

    fun map (fromT:EventToday, fromP:EventParams):EventInfoUi{
        return EventInfoUi(
            id =fromT.eventId ,
            dbId =fromT.dbId ?:-1 ,
            name = fromP.eventName ,
            color =fromP.eventColor ,
            date =fromT.date,
            description =fromT.description ,
            spentTime = fromT.time
        )
    }
}