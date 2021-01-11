package com.example.shared.domain.events.eventToday

import com.example.shared.data.db.events.dao.EventDao
import com.example.shared.data.entity.event.EventChartInfoUi
import com.example.shared.data.entity.event.EventTodayUi
import com.example.shared.di.components.DaggerSharedComponent
import com.example.shared.repository.events.EventRepository
import javax.inject.Inject

class LoadChartInfoListUseCase() {

    @Inject
    lateinit var eventRepository: EventRepository

    init {
        DaggerSharedComponent
            .create()
            .initializeLoadChartInfoListUseCase(this)
    }

    suspend fun doWork(params: Params): List<EventChartInfoUi> {
        return eventRepository.getAllEventTimeByDate(params.date, params.dao)
    }

    data class Params(
        val date: String,
        val dao: EventDao
    )
}