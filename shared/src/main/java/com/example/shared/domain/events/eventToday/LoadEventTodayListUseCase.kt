package com.example.shared.domain.events.eventToday

import com.example.shared.data.db.events.dao.EventDao
import com.example.shared.data.entity.event.EventTodayUi
import com.example.shared.di.components.DaggerSharedComponent
import com.example.shared.repository.events.EventRepository
import javax.inject.Inject

class LoadEventTodayListUseCase() {

    @Inject
    lateinit var eventRepository: EventRepository

    init {
        DaggerSharedComponent
            .create()
            .initializeLoadEventTodayListUseCase(this)
    }

    suspend fun doWork(params: Params): List<EventTodayUi> {
        return eventRepository.getEventsTodayByData(params.date, params.dao)
    }

    data class Params(
        val date: String,
        val dao: EventDao
    )
}