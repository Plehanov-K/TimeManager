package com.example.shared.data.entity.event

import androidx.lifecycle.MutableLiveData


data class EventTodayUi(
    var id: Int,
    var timeSpent: MutableLiveData<Int> = MutableLiveData(0),
)

