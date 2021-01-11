package com.example.shared.domain.events.scheduled

import com.example.shared.data.db.events.dao.EventDao
import com.example.shared.data.db.events.entity.ScheduledTime
import com.example.shared.di.components.DaggerSharedComponent
import com.example.shared.repository.events.EventRepository
import javax.inject.Inject

class DelScheduledTimeUseCase () {

    @Inject
    lateinit var eventRepository: EventRepository

    init {
        DaggerSharedComponent
            .create()
            .initializeDelScheduledTimeUseCase(this)
    }

    suspend fun doWork(params: Params) {
        return eventRepository.delScheduledTime(params.scheduledTime,params.dao)
    }

    data class Params(
        val scheduledTime: ScheduledTime,
        val dao: EventDao
    )
}