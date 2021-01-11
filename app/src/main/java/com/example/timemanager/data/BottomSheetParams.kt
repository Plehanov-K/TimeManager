package com.example.timemanager.data

import android.graphics.drawable.Drawable

data class BottomSheetParams(
    val eventId: Int = -1,
    val color: Int = 0,
    val name: String? = "",
    val idDb: Int = -1,
    val timeSpent: Int = 0,
    val description : String = ""
)