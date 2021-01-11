package com.example.shared.data.db.events.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_today_table")
data class EventToday(
    @ColumnInfo(name = "event_date")
    val date: String,
    @ColumnInfo(name = "event_id")
    val eventId: Int,
    @ColumnInfo(name = "event_time")
    val time: Int,
    @ColumnInfo(name = "event_description")
    val description: String,
    @PrimaryKey(autoGenerate = true)
    var dbId: Int? = null
)

