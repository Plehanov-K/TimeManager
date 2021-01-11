package com.example.shared.domain.events.params

import com.example.shared.data.db.events.dao.EventDao
import com.example.shared.data.entity.event.EventParamsUi
import com.example.shared.di.components.DaggerSharedComponent
import com.example.shared.repository.events.EventRepository
import javax.inject.Inject

class LoadEventParamsListUseCase() {

    @Inject
    lateinit var eventRepository: EventRepository

    init {
        DaggerSharedComponent
            .create()
            .initializeLoadEventParamsListUseCase(this)
    }

    suspend fun doWork(params: Params): List<EventParamsUi> {
        return eventRepository.getAllEventParams(params.dao)
    }

    data class Params(
        val dao: EventDao
    )
}