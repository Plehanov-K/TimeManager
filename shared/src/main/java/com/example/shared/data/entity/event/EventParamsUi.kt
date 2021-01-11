package com.example.shared.data.entity.event

import androidx.lifecycle.MutableLiveData

data class EventParamsUi(
    val id :Int,
    val name : MutableLiveData<String>,
    val color : MutableLiveData<String>
)
