package com.example.timemanager.data

import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.*

object MDate {
    private val sdfMonth = SimpleDateFormat("MMMM")
    private val sdf = SimpleDateFormat("dd/MM/yyyy")
    private var calendar = Calendar.getInstance()
    private var mTimeInMillis = calendar.timeInMillis
    val date = MutableLiveData(sdf.format(mTimeInMillis))
    var dateM: String = sdfMonth.format(mTimeInMillis)
    var maxDayInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)


    fun setDate(time: Long) {
        mTimeInMillis = mTimeInMillis.plus(time)
        date.postValue(sdf.format(mTimeInMillis))
        dateM = sdfMonth.format(mTimeInMillis)
        calendar.timeInMillis = mTimeInMillis
        maxDayInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }
}