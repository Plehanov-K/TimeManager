package com.example.timemanager.data

import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.*

const val CAL_DAY = Calendar.DATE
const val CAL_MONTH = Calendar.MONTH

object MDate {
    private val sdf = SimpleDateFormat("dd/MM/yyyy HH : mm : ss")
    private var calendar = Calendar.getInstance(Locale.getDefault())
    val date = MutableLiveData(sdf.format(calendar.timeInMillis))
    var maxDayInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    var tempTime = Calendar.MONTH


    fun setDate(time: Int) {
        calendar.add(tempTime,time)
        date.postValue(sdf.format(calendar.timeInMillis))
        maxDayInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }
}