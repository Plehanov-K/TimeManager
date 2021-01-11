package com.example.shared.domain.events.params

import com.example.shared.data.db.events.dao.EventDao
import com.example.shared.di.components.DaggerSharedComponent
import com.example.shared.repository.events.EventRepository
import javax.inject.Inject

class UpdateEventParamsNameUseCase () {

    @Inject
    lateinit var eventRepository: EventRepository

    init {
        DaggerSharedComponent
            .create()
            .initializeUpdateEventParamsNameUseCase (this)
    }

    suspend fun doWork(params: Params) {
        return eventRepository.updateEventParamsName(params.name,params.id,params.dao)
    }

    data class Params(
        val id:Int,
        val name:String,
        val dao: EventDao
    )
}