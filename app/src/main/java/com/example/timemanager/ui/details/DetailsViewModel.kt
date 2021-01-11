package com.example.timemanager.ui.details

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shared.data.db.Db
import com.example.shared.data.entity.event.EventInfoUi
import com.example.shared.domain.events.eventToday.DelEventTodayUseCase
import com.example.shared.domain.events.eventToday.LoadEventInfoListUseCase
import com.example.shared.domain.events.eventToday.UpdateEventTodayUseCase
import com.example.timemanager.data.BottomSheetParams
import com.example.timemanager.data.MDate
import com.example.timemanager.utils.launchForResult
import com.example.timemanager.utils.launchIo
import com.example.timemanager.utils.launchUi

class DetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = Db.getDb(application.applicationContext).eventDao()
    private val loadEventInfoListUseCase = LoadEventInfoListUseCase()
    private val delEventTodayUseCase = DelEventTodayUseCase()
    private val updateEventTodayUseCase = UpdateEventTodayUseCase()


    var dayTimeInMinutes : Int = 1440

    val listEventInfo = MutableLiveData<List<EventInfoUi>>()

    private val _tempEvent = MutableLiveData<BottomSheetParams>()
    val tempEvent: LiveData<BottomSheetParams> = _tempEvent

    init {
        loadEventTodayFromDb()
    }

    fun loadEventTodayFromDb() {
        launchIo {
            launchForResult {
                val list =
                    loadEventInfoListUseCase.doWork(LoadEventInfoListUseCase.Params("${MDate.date.value.toString().substringBefore(" ")}%", dao))
                launchUi {
                    listEventInfo.postValue(list)
                    var temp = 1440
                    list.forEach {
                        temp -= it.spentTime
                    }
                   dayTimeInMinutes=temp
                }
            }
        }
    }

    fun deleteEventFromDb(id: Int) {
        launchIo {
            delEventTodayUseCase.doWork(DelEventTodayUseCase.Params(dbId = id, dao))
            loadEventTodayFromDb()
        }
    }

    fun updateEventFromDb(id: Int, time: Int, description: String) {
        launchIo {
            updateEventTodayUseCase.doWork(
                UpdateEventTodayUseCase.Params(
                    id = id,
                    time = time,
                    description = description,
                    dao = dao
                )
            )
            loadEventTodayFromDb()
        }
    }

    fun openBottomSheet(id: Int) {
        val eventInfoById = listEventInfo.value?.get(id)
        _tempEvent.postValue(
            eventInfoById?.dbId?.let {
                BottomSheetParams(
                    color = Color.parseColor(eventInfoById.color),
                    name = eventInfoById.name,
                    idDb = eventInfoById.dbId,
                    timeSpent = eventInfoById.spentTime,
                    description = eventInfoById.description
                )
            }
        )
    }
}