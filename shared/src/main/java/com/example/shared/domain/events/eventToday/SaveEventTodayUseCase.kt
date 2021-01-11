package com.example.shared.domain.events.eventToday

import com.example.shared.data.db.events.dao.EventDao
import com.example.shared.data.db.events.entity.EventToday
import com.example.shared.di.components.DaggerSharedComponent
import com.example.shared.repository.events.EventRepository
import javax.inject.Inject

class SaveEventTodayUseCase () {

    @Inject
    lateinit var eventRepository: EventRepository

    init {
        DaggerSharedComponent
            .create()
            .initializeSaveEventTodayUseCase(this)
    }

    suspend fun doWork(params: Params) {
        return eventRepository.saveEventToday(params.eventToday,params.dao)
    }

    data class Params(
        val eventToday: EventToday,
        val dao: EventDao
    )
}