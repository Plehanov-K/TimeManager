package com.example.shared.repository.events

import com.example.shared.data.db.events.dao.EventDao
import com.example.shared.data.db.events.entity.EventParams
import com.example.shared.data.db.events.entity.EventToday
import com.example.shared.data.db.events.entity.ScheduledTime
import com.example.shared.data.entity.event.EventScheduledTimeUi
import javax.inject.Inject

class EventDataSource @Inject constructor(

) {
    suspend fun saveEventToday(eventToday: EventToday, dao: EventDao) {
        dao.addEventToday(eventToday)
    }

    suspend fun saveScheduledTime(scheduledTime: ScheduledTime, dao: EventDao) {
        dao.addScheduledTime(scheduledTime)
    }

    suspend fun saveEventParams(eventParams: EventParams, dao: EventDao) {
        dao.addEventParams(eventParams)
    }

    suspend fun updateEventParamName(name: String, id: Int, dao: EventDao){
        return dao.updateEventParamName(name,id)
    }

    suspend fun updateEventParamColor(color: String, id: Int, dao: EventDao){
        return dao.updateEventParamColor(color,id)
    }

    suspend fun updateEventToday(description: String,time:Int, id: Int, dao: EventDao){
        return dao.updateEventToday(description,time,id)
    }

    suspend fun updateScheduledTime(scheduledTime: Int, id: Int, dao: EventDao){
        return dao.updateScheduledTime(scheduledTime,id)
    }

    suspend fun delEventToday(dbId: Int?, dao: EventDao) {
        dao.deleteEventToday(dbId)
    }

    suspend fun delScheduledTime(scheduledTime: ScheduledTime, dao: EventDao) {
        dao.deleteScheduledTime(scheduledTime)
    }

    suspend fun getEventsTodayByData(date: String, dao: EventDao): List<EventToday> {
        return dao.getEventTodayByDate(date)
    }

    suspend fun getScheduledTimeByDate(date: String, dao: EventDao): List<ScheduledTime> {
        return dao.getScheduledTimeByDate(date)
    }

    suspend fun getAllEventParams(dao: EventDao): List<EventParams>{
        return dao.getAllEventParams()
    }
}