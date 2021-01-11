package com.example.shared.data.entity.event

import androidx.lifecycle.MutableLiveData

data class EventScheduledTimeUi(
    var id: Int,
    val timeSchedule: MutableLiveData<Int> = MutableLiveData(0),
)