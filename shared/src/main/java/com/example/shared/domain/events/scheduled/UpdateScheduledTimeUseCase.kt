package com.example.shared.domain.events.scheduled

import com.example.shared.data.db.events.dao.EventDao
import com.example.shared.di.components.DaggerSharedComponent
import com.example.shared.repository.events.EventRepository
import javax.inject.Inject

class UpdateScheduledTimeUseCase () {

    @Inject
    lateinit var eventRepository: EventRepository

    init {
        DaggerSharedComponent
            .create()
            .initializeUpdateScheduledTimeUseCase (this)
    }

    suspend fun doWork(params: Params) {
        return eventRepository.updateScheduledTime(params.scheduledTime,params.id,params.dao)
    }

    data class Params(
        val id:Int,
        val scheduledTime:Int,
        val dao: EventDao
    )
}