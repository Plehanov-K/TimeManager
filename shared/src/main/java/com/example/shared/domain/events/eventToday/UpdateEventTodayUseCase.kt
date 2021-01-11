package com.example.shared.domain.events.eventToday

import com.example.shared.data.db.events.dao.EventDao
import com.example.shared.di.components.DaggerSharedComponent
import com.example.shared.repository.events.EventRepository
import javax.inject.Inject

class UpdateEventTodayUseCase () {

    @Inject
    lateinit var eventRepository: EventRepository

    init {
        DaggerSharedComponent
            .create()
            .initializeUpdateEventTodayUseCase (this)
    }

    suspend fun doWork(params: Params) {
        return eventRepository.updateEventToday(params.description,params.time,params.id,params.dao)
    }

    data class Params(
        val id:Int,
        val time:Int,
        val description:String,
        val dao: EventDao
    )
}