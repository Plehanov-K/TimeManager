package com.example.shared.di.components

import com.example.shared.domain.events.eventToday.*
import com.example.shared.domain.events.params.LoadEventParamsListUseCase
import com.example.shared.domain.events.params.SaveEventParamsUseCase
import com.example.shared.domain.events.params.UpdateEventParamsColorUseCase
import com.example.shared.domain.events.params.UpdateEventParamsNameUseCase
import com.example.shared.domain.events.scheduled.*
import dagger.Component

@Component
interface SharedComponent {

    fun initializeDelEventTodayUseCase(
       delEventTodayUseCase: DelEventTodayUseCase
    )

    fun initializeLoadEventInfoListUseCase(
        loadEventInfoListUseCase: LoadEventInfoListUseCase
    )

    fun initializeLoadEventTodayListUseCase(
        loadEventTodayListUseCase: LoadEventTodayListUseCase
    )

    fun initializeSaveEventTodayUseCase(
        saveEventTodayUseCase: SaveEventTodayUseCase
    )

    fun initializeLoadEventParamsListUseCase(
        loadEventParamsListUseCase: LoadEventParamsListUseCase
    )

    fun initializeLoadChartInfoListUseCase(
        loadChartInfoListUseCase: LoadChartInfoListUseCase
    )

    fun initializeLoadScheduledTimeInfoListUseCase(
        loadScheduledTimeInfoListUseCase: LoadScheduledTimeInfoListUseCase
    )

    fun initializeSaveEventParamsUseCase(
         saveEventParamsUseCase: SaveEventParamsUseCase
    )

    fun initializeUpdateEventParamsNameUseCase(
        updateEventParamsNameUseCase: UpdateEventParamsNameUseCase
    )

    fun initializeUpdateEventParamsColorUseCase(
        updateEventParamsColorUseCase: UpdateEventParamsColorUseCase
    )

    fun initializeUpdateEventTodayUseCase(
        updateEventTodayUseCase: UpdateEventTodayUseCase
    )

    fun initializeUpdateScheduledTimeUseCase(
        updateScheduledTimeUseCase: UpdateScheduledTimeUseCase
    )

    fun initializeDelScheduledTimeUseCase(
        delScheduledTimeUseCase: DelScheduledTimeUseCase
    )

    fun initializeLoadScheduledTimeListUseCase(
        loadScheduledTimeListUseCase: LoadScheduledTimeListUseCase
    )

    fun initializeSaveScheduledTimeUseCase(
        saveScheduledTimeUseCase: SaveScheduledTimeUseCase
    )
}