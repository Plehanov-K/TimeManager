package com.example.timemanager.ui.planning

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shared.data.db.Db
import com.example.shared.data.db.events.entity.ScheduledTime
import com.example.shared.data.entity.event.EventScheduledInfoUi
import com.example.shared.domain.events.scheduled.LoadScheduledTimeInfoListUseCase
import com.example.shared.domain.events.scheduled.SaveScheduledTimeUseCase
import com.example.shared.domain.events.scheduled.UpdateScheduledTimeUseCase
import com.example.timemanager.data.BottomSheetParams
import com.example.timemanager.data.MDate
import com.example.timemanager.utils.launchForResult
import com.example.timemanager.utils.launchIo
import com.example.timemanager.utils.launchUi

class PlanningViewModel(application: Application) : AndroidViewModel(application) {

    private val loadScheduledTimeInfoListUseCase = LoadScheduledTimeInfoListUseCase()
    private val saveScheduledTimeUseCase = SaveScheduledTimeUseCase()
    private val updateScheduledTimeUseCase = UpdateScheduledTimeUseCase()
    private val dao = Db.getDb(application.applicationContext).eventDao()

    val listScheduledInfo = MutableLiveData<List<EventScheduledInfoUi>>()

    var totalTimeMonth = MDate.maxDayInMonth.times(1440)

    private val _tempEvent = MutableLiveData<BottomSheetParams>()
    val tempEvent: LiveData<BottomSheetParams> = _tempEvent

    init {
        loadScheduledInfoList()
    }

    fun loadScheduledInfoList() {
        launchIo {
            launchForResult {
                val dateMonth = MDate.date.value.toString()
                    .substringAfter("/").substringBefore(" ")
                val tempList = loadScheduledTimeInfoListUseCase
                    .doWork(LoadScheduledTimeInfoListUseCase.Params("%$dateMonth%", dao))
                launchUi {
                    var tempTime = MDate.maxDayInMonth.times(1440)
                    tempList.forEach {
                        tempTime -= it.scheduledTime
                    }
                    totalTimeMonth = tempTime
                    listScheduledInfo.postValue(tempList)
                }
            }
        }
    }

    fun openBottomSheet(id: Int) {
        val scheduledInfo = listScheduledInfo.value?.get(id)
        if (scheduledInfo != null) {
            _tempEvent.postValue(
                BottomSheetParams(
                    color = Color.parseColor(scheduledInfo.color),
                    name = scheduledInfo.name,
                    idDb = scheduledInfo.dbId,
                    eventId = scheduledInfo.id
                )
            )
        }
    }

    fun addTimeScheduledInDb(idDb: Int, timeScheduled: Int) {
        launchIo {
            launchForResult {
                if (idDb == -1) {
                    tempEvent.value?.eventId?.let {
                        ScheduledTime(
                            date = MDate.date.value.toString(),
                            eventId = it,
                            scheduledTime = timeScheduled,
                        )
                    }?.let {
                        SaveScheduledTimeUseCase.Params(
                            it, dao
                        )
                    }?.let {
                        saveScheduledTimeUseCase.doWork(
                            it
                        )
                    }
                } else {
                    updateScheduledTimeUseCase.doWork(
                        UpdateScheduledTimeUseCase.Params(
                            idDb,
                            timeScheduled,
                            dao
                        )
                    )
                }
                loadScheduledInfoList()
            }
        }
    }
}
