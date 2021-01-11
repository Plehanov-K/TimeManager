package com.example.shared.data.mappers.event

import androidx.lifecycle.MutableLiveData
import com.example.shared.data.db.events.entity.EventParams
import com.example.shared.data.entity.event.EventParamsUi
import javax.inject.Inject

class EventParamsFromEventDbEntityMapper @Inject constructor() {

    fun map (from: EventParams): EventParamsUi {
        return EventParamsUi(
            id = from.eventId,
            name = MutableLiveData(from.eventName),
            color = MutableLiveData(from.eventColor)
        )
    }
}