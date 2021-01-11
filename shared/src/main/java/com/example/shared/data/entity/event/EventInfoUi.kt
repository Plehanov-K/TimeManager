package com.example.shared.data.entity.event

data class EventInfoUi(
    val id: Int,
    val dbId:Int,
    val name: String,
    val color:String,
    val date: String,
    val description: String,
    val spentTime: Int
)