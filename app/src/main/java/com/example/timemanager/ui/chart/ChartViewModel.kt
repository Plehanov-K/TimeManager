package com.example.timemanager.ui.chart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.shared.data.db.Db
import com.example.shared.data.entity.event.EventChartInfoUi
import com.example.shared.domain.events.eventToday.LoadChartInfoListUseCase
import com.example.timemanager.data.MDate
import com.example.timemanager.utils.launchIo
import java.text.SimpleDateFormat
import java.util.*

class ChartViewModel(application: Application) : AndroidViewModel(application) {
    private val loadChartInfoListUseCase = LoadChartInfoListUseCase()

    private val dao = Db.getDb(application.applicationContext).eventDao()

    val listChartInfo = MutableLiveData<List<EventChartInfoUi>>()
    var totalTime = 0

    init {
        loadChartInfoList()
    }

    fun loadChartInfoList() {
        launchIo {
            var tempTotalTime = 0
            val tempTimeMonth =MDate.date.value.toString().substringAfter("/").substringBefore(" ")
            val tempList = loadChartInfoListUseCase.doWork(
                LoadChartInfoListUseCase.Params("%$tempTimeMonth%", dao)
            )
            tempList.forEach {
                tempTotalTime += it.spentTime
            }
            listChartInfo.postValue(tempList.sortedByDescending { it.spentTime })
            totalTime = tempTotalTime
        }
    }
}

