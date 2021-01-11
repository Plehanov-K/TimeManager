package com.example.shared.data.entity.event

data class EventScheduledInfoUi(
    val id: Int,
    val dbId: Int,
    val name: String,
    val color: String,
    val scheduledTime: Int,
    val spentTime: Int
)