package com.example.shared.domain.events.scheduled

import com.example.shared.data.db.events.dao.EventDao
import com.example.shared.data.db.events.entity.ScheduledTime
import com.example.shared.data.entity.event.EventScheduledTimeUi
import com.example.shared.di.components.DaggerSharedComponent
import com.example.shared.repository.events.EventRepository
import javax.inject.Inject

class LoadScheduledTimeListUseCase() {

    @Inject
    lateinit var eventRepository: EventRepository

    init {
        DaggerSharedComponent
            .create()
            .initializeLoadScheduledTimeListUseCase(this)
    }

    suspend fun doWork(params: Params): List<EventScheduledTimeUi> {
        return eventRepository.getScheduledTimeByDate(params.date, params.dao)
    }

    data class Params(
        val date: String,
        val dao: EventDao
    )
}