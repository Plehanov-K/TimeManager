package com.example.shared.domain.events.eventToday

import com.example.shared.data.db.events.dao.EventDao
import com.example.shared.data.db.events.entity.EventParams
import com.example.shared.data.entity.event.EventInfoUi
import com.example.shared.data.entity.event.EventTodayUi
import com.example.shared.di.components.DaggerSharedComponent
import com.example.shared.repository.events.EventRepository
import javax.inject.Inject

class LoadEventInfoListUseCase() {

    @Inject
    lateinit var eventRepository: EventRepository

    init {
        DaggerSharedComponent
            .create()
            .initializeLoadEventInfoListUseCase(this)
    }

    suspend fun doWork(params: Params): List<EventInfoUi> {
        return eventRepository.getAllEventsInfoByData(
            params.date,
            params.dao,
        )
    }

    data class Params(
        val date: String,
        val dao: EventDao,
    )
}