package com.example.shared.data.db.events.dao

import androidx.room.*
import com.example.shared.data.db.events.entity.EventParams
import com.example.shared.data.db.events.entity.EventToday
import com.example.shared.data.db.events.entity.ScheduledTime
import com.example.shared.data.entity.event.EventScheduledTimeUi


@Dao
interface EventDao {
    @Insert
    fun addEventToday(event: EventToday)

    @Insert
    fun addScheduledTime(scheduledTime: ScheduledTime)

    @Insert
    fun addEventParams(eventParams: EventParams)

    @Query("DELETE FROM event_today_table WHERE dbId =:dbId")
    fun deleteEventToday(dbId: Int?)

    @Query("UPDATE event_params_table SET event_color =:color WHERE event_id =:id")
    fun updateEventParamColor(color: String, id: Int)

    @Query("UPDATE event_params_table SET event_name =:name WHERE event_id =:id")
    fun updateEventParamName(name: String, id: Int)

    @Query("UPDATE event_today_table SET event_description =:description, event_time =:time  WHERE dbId =:id")
    fun updateEventToday(description: String, time: Int, id: Int)

    @Query("UPDATE scheduled_time_table SET scheduled_time =:scheduledTime WHERE idDb =:id")
    fun updateScheduledTime(scheduledTime : Int, id: Int)

    @Delete
    fun deleteScheduledTime(scheduledTime: ScheduledTime)

    @Query("SELECT * FROM event_today_table WHERE event_date LIKE :date")
    fun getEventTodayByDate(date: String): List<EventToday>

    @Query("SELECT * FROM scheduled_time_table WHERE date LIKE :date")
    fun getScheduledTimeByDate(date: String): List<ScheduledTime>

    @Query("SELECT * FROM event_params_table")
    fun getAllEventParams(): List<EventParams>
}