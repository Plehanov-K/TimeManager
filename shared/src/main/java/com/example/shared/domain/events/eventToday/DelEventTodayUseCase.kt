package com.example.shared.domain.events.eventToday

import com.example.shared.data.db.events.dao.EventDao
import com.example.shared.di.components.DaggerSharedComponent
import com.example.shared.repository.events.EventRepository
import javax.inject.Inject

class DelEventTodayUseCase () {

    @Inject
    lateinit var eventRepository: EventRepository

    init {
        DaggerSharedComponent
            .create()
            .initializeDelEventTodayUseCase(this)
    }

    suspend fun doWork(params: Params) {
        return eventRepository.delEventToday(params.dbId,params.dao)
    }

    data class Params(
        val dbId: Int?,
        val dao: EventDao
    )
}