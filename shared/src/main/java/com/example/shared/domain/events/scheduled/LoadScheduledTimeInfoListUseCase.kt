package com.example.shared.domain.events.scheduled

import com.example.shared.data.db.events.dao.EventDao
import com.example.shared.data.db.events.entity.ScheduledTime
import com.example.shared.data.entity.event.EventScheduledInfoUi
import com.example.shared.data.entity.event.EventScheduledTimeUi
import com.example.shared.di.components.DaggerSharedComponent
import com.example.shared.repository.events.EventRepository
import javax.inject.Inject

class LoadScheduledTimeInfoListUseCase() {

    @Inject
    lateinit var eventRepository: EventRepository

    init {
        DaggerSharedComponent
            .create()
            .initializeLoadScheduledTimeInfoListUseCase(this)
    }

    suspend fun doWork(params: Params): List<EventScheduledInfoUi> {
        return eventRepository.getScheduledInfoByDate(params.date, params.dao)
    }

    data class Params(
        val date: String,
        val dao: EventDao
    )
}