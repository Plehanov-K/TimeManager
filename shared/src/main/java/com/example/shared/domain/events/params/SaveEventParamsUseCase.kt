package com.example.shared.domain.events.params

import com.example.shared.data.db.events.dao.EventDao
import com.example.shared.data.db.events.entity.EventParams
import com.example.shared.di.components.DaggerSharedComponent
import com.example.shared.repository.events.EventRepository
import javax.inject.Inject

class SaveEventParamsUseCase () {

    @Inject
    lateinit var eventRepository: EventRepository

    init {
        DaggerSharedComponent
            .create()
            .initializeSaveEventParamsUseCase(this)
    }

    suspend fun doWork(params: Params) {
        return eventRepository.saveEventParams(params.eventParams,params.dao)
    }

    data class Params(
        val eventParams: EventParams,
        val dao: EventDao
    )
}