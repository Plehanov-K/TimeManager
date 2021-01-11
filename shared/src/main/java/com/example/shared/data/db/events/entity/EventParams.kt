package com.example.shared.data.db.events.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_PARAMS_NAME = "event_params_table"
const val COLUMN_PARAMS_ID= "event_id"
const val COLUMN_PARAMS_NAME= "event_name"
const val COLUMN_PARAMS_COLOR= "event_color"

@Entity(tableName = TABLE_PARAMS_NAME)
data class EventParams(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_PARAMS_ID)
    val eventId: Int,
    @ColumnInfo(name = COLUMN_PARAMS_NAME)
    val eventName: String,
    @ColumnInfo(name = COLUMN_PARAMS_COLOR)
    val eventColor: String,
    )

