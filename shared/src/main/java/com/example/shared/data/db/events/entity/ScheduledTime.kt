package com.example.shared.data.db.events.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scheduled_time_table")
data class ScheduledTime(
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "event_id")
    val eventId: Int,
    @ColumnInfo(name = "scheduled_time")
    val scheduledTime: Int,
    @PrimaryKey(autoGenerate = true)
    var idDb: Int? = null
)

