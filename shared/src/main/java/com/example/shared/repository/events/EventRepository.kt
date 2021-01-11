package com.example.shared.repository.events

import androidx.lifecycle.MutableLiveData
import com.example.shared.data.db.events.dao.EventDao
import com.example.shared.data.db.events.entity.EventParams
import com.example.shared.data.db.events.entity.EventToday
import com.example.shared.data.db.events.entity.ScheduledTime
import com.example.shared.data.entity.event.*
import com.example.shared.data.mappers.event.*
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val eventDataSource: EventDataSource,
    private val eventParamsFromEventDbEntityMapper: EventParamsFromEventDbEntityMapper,
    private val eventTodayFromEventDbMapper: EventTodayFromEventDbMapper,
    private val eventInfoFromParamsAndTodayEntityMapper: EventInfoFromParamsAndTodayEntityMapper,
    private val scheduledTimeUiFromScheduledTimeDbMapper: ScheduledTimeUiFromScheduledTimeDbMapper,
    private val scheduledInfoFromParamsTodayScheduledMapper: ScheduledInfoFromParamsTodayScheduledMapper,
    private val chartInfoFromParamsAndTodayEntityMapper: ChartInfoFromParamsAndTodayEntityMapper
) {
    suspend fun saveEventToday(eventToday: EventToday, dao: EventDao) {
        eventDataSource.saveEventToday(eventToday, dao)
    }

    suspend fun saveScheduledTime(scheduledTime: ScheduledTime, dao: EventDao) {
        eventDataSource.saveScheduledTime(scheduledTime, dao)
    }

    suspend fun saveEventParams(eventParams: EventParams, dao: EventDao) {
        eventDataSource.saveEventParams(eventParams, dao)
    }

    suspend fun updateEventParamsName(name: String, id: Int, dao: EventDao) {
        return eventDataSource.updateEventParamName(name, id, dao)
    }

    suspend fun updateEventParamsColor(color: String, id: Int, dao: EventDao) {
        return eventDataSource.updateEventParamColor(color, id, dao)
    }

    suspend fun updateEventToday(description: String, time: Int, id: Int, dao: EventDao) {
        return eventDataSource.updateEventToday(description, time, id, dao)
    }

    suspend fun updateScheduledTime(scheduledTime: Int, id: Int, dao: EventDao) {
        return eventDataSource.updateScheduledTime(scheduledTime, id, dao)
    }

    suspend fun delEventToday(dbId: Int?, dao: EventDao) {
        eventDataSource.delEventToday(dbId, dao)
    }

    suspend fun delScheduledTime(scheduledTime: ScheduledTime, dao: EventDao) {
        eventDataSource.delScheduledTime(scheduledTime, dao)
    }

    suspend fun getAllEventsInfoByData(date: String, dao: EventDao): List<EventInfoUi> {
        val listParams = eventDataSource.getAllEventParams(dao)
        val listEventInfoUi = mutableListOf<EventInfoUi>()
        eventDataSource.getEventsTodayByData(date, dao).map { eventToday ->
            listParams.forEach { eventParams ->
                if (eventToday.eventId == eventParams.eventId) {
                    listEventInfoUi.add(
                        eventInfoFromParamsAndTodayEntityMapper.map(
                            eventToday,
                            eventParams
                        )
                    )
                }
            }
        }
        return listEventInfoUi
    }

    suspend fun getEventsTodayByData(date: String, dao: EventDao): List<EventTodayUi> {
        val listEventTodayUi = mutableListOf<EventTodayUi>()
        val tempList = eventDataSource.getEventsTodayByData(date, dao).map {
            eventTodayFromEventDbMapper.map(it)
        }
        for (i in 0..11) {
            var tempTimeSpent = 0
            tempList.forEach {
                if (it.id == i) {
                    tempTimeSpent += it.timeSpent.value ?: 0
                }
            }
            listEventTodayUi.add(EventTodayUi(id = i, MutableLiveData(tempTimeSpent)))
        }
        return listEventTodayUi
    }

    suspend fun getScheduledTimeByDate(date: String, dao: EventDao): List<EventScheduledTimeUi> {
        val listScheduledTimeUi = mutableListOf<EventScheduledTimeUi>()
        val tempList = eventDataSource.getScheduledTimeByDate(date, dao).map {
            scheduledTimeUiFromScheduledTimeDbMapper.map(it)
        }
        for (i in 0..11) {
            var tempScheduledTime = 0
            tempList.forEach {
                if(it.id == i) tempScheduledTime = it.timeSchedule.value ?:0
            }
            listScheduledTimeUi.add(EventScheduledTimeUi(i, MutableLiveData(tempScheduledTime)))
        }
        return listScheduledTimeUi
    }


    suspend fun getScheduledInfoByDate(date: String, dao: EventDao): List<EventScheduledInfoUi> {
        val listParam = eventDataSource.getAllEventParams(dao)
        val listEventToday = eventDataSource.getEventsTodayByData(date, dao)
        val listScheduledTime = eventDataSource.getScheduledTimeByDate(date, dao)
        return listParam.map { it1 ->
            var scheduledTime = ScheduledTime(date, 0, 0)
            var spentTime = 0
            listScheduledTime.forEach { it2 ->
                if (it2.eventId == it1.eventId) {
                    scheduledTime = it2
                }
            }
            listEventToday.forEach { it3 ->
                if (it1.eventId == it3.eventId) {
                    spentTime += it3.time
                }
            }
            return@map scheduledInfoFromParamsTodayScheduledMapper.map(
                spentTime,
                it1,
                scheduledTime
            )
        }
    }

    suspend fun getAllEventParams(dao: EventDao): List<EventParamsUi> {
        return eventDataSource.getAllEventParams(dao).map {
            eventParamsFromEventDbEntityMapper.map(it)
        }
    }

    suspend fun getAllEventTimeByDate(date: String, dao: EventDao): List<EventChartInfoUi> {
        val listParam = eventDataSource.getAllEventParams(dao)
        val eventList = eventDataSource.getEventsTodayByData(date, dao)
        return listParam.map { eParam ->
            var tempTime = 0
            eventList.forEach { eTime ->
                if (eTime.eventId == eParam.eventId) {
                    tempTime += eTime.time
                }
            }
            chartInfoFromParamsAndTodayEntityMapper.map(tempTime, eParam)
        }
    }
}