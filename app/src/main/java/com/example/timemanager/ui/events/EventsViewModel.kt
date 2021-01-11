package com.example.timemanager.ui.events

import android.app.Application
import android.graphics.Color
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shared.data.db.Db
import com.example.shared.data.db.events.entity.EventToday
import com.example.shared.data.entity.event.EventTodayUi
import com.example.shared.data.entity.event.EventParamsUi
import com.example.shared.data.entity.event.EventScheduledTimeUi
import com.example.shared.domain.events.eventToday.LoadEventTodayListUseCase
import com.example.shared.domain.events.eventToday.SaveEventTodayUseCase
import com.example.shared.domain.events.params.LoadEventParamsListUseCase
import com.example.shared.domain.events.params.UpdateEventParamsColorUseCase
import com.example.shared.domain.events.params.UpdateEventParamsNameUseCase
import com.example.shared.domain.events.scheduled.LoadScheduledTimeListUseCase
import com.example.timemanager.data.BottomSheetParams
import com.example.timemanager.data.MDate
import com.example.timemanager.utils.launchForResult
import com.example.timemanager.utils.launchIo
import com.example.timemanager.utils.launchUi
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventsViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = Db.getDb(application.applicationContext).eventDao()
    private val saveEventTodayUseCase = SaveEventTodayUseCase()
    private val loadEventTodayListUseCase = LoadEventTodayListUseCase()
    private val loadEventParamsListUseCase = LoadEventParamsListUseCase()
    private val updateEventParamsNameUseCase = UpdateEventParamsNameUseCase()
    private val updateEventParamsColorUseCase = UpdateEventParamsColorUseCase()
    private val loadScheduledTimeListUseCase = LoadScheduledTimeListUseCase()

    var listEventToday = mutableListOf<EventTodayUi>()
    var listEventScheduledTime = mutableListOf<EventScheduledTimeUi>()
    var listParamsEvent = mutableListOf<EventParamsUi>()

    private val _tempEvent = MutableLiveData<BottomSheetParams>()
    val tempEvent: LiveData<BottomSheetParams> = _tempEvent

    private var _dayTimeInMinutes = MutableLiveData<Int>()
    val dayTimeInMinutes: LiveData<Int> = _dayTimeInMinutes

    val actionList = MutableLiveData<List<PieEntry>>().apply {
        value = mutableListOf()
    }
    val listPieColors = MutableLiveData<List<Int>>().apply {
        value = mutableListOf()
    }

    init {
        loadEventTodayFromDb()
        launchIo {
            launchForResult {
                listParamsEvent = loadParamsFromDb() as MutableList<EventParamsUi>
            }
            updatePieColor()
        }
        for (i in 0..11) {
            listEventToday.add(EventTodayUi(id = i, MutableLiveData(0)))
            listEventScheduledTime.add(EventScheduledTimeUi(id = i, MutableLiveData(0)))
        }
    }

    suspend fun loadParamsFromDb(): List<EventParamsUi> {
        return loadEventParamsListUseCase.doWork(LoadEventParamsListUseCase.Params(dao))
    }


    fun updateEventParamNameDb(name: String, id: Int) {
        launchIo {
            launchForResult {
                updateEventParamsNameUseCase.doWork(
                    UpdateEventParamsNameUseCase.Params(
                        id,
                        name,
                        dao
                    )
                )
                launchIo {
                    listParamsEvent[id].name.postValue(loadParamsFromDb()[id].name.value)
                }
            }
        }
    }


    fun updateEventParamColorDb(color: String, id: Int) {
        launchIo {
            launchForResult {
                updateEventParamsColorUseCase.doWork(
                    UpdateEventParamsColorUseCase.Params(
                        id,
                        color,
                        dao
                    )
                )
                listParamsEvent[id].color.postValue(loadParamsFromDb()[id].color.value)
                launchUi {
                    updatePieColor()
                }
            }
        }
    }


    fun updatePieColor() {
        val tempList = mutableListOf<Int>()
        listParamsEvent.forEach {
            tempList.add(Color.parseColor(it.color.value))
        }
        tempList.add(Color.parseColor("#4E99E6"))
        listPieColors.postValue(tempList)
    }


    fun updatePieData() {
        val tempList = mutableListOf<PieEntry>()
        listEventToday.forEach {
            tempList.add(PieEntry(it.timeSpent.value?.toFloat() ?: 0.0f, it.id))
        }
        tempList.add(PieEntry(dayTimeInMinutes.value?.toFloat() ?: 0.0f))
        actionList.postValue(tempList)
    }


    fun saveEventTodayToDb(spentTime: Int, description: String) {
        CoroutineScope(Dispatchers.IO).launch {
            tempEvent.value?.let {
                if (spentTime != 0) {
                    saveEventTodayUseCase.doWork(
                        SaveEventTodayUseCase.Params(
                            EventToday(
                                date = MDate.date.value.toString(),
                                eventId = it.eventId,
                                time = spentTime,
                                description = description
                            ), dao
                        )
                    )
                    loadEventTodayFromDb()
                }
            }
        }
    }


    fun loadEventTodayFromDb() {
        launchIo {
            launchForResult {
                val listEventFromDb = loadEventTodayListUseCase
                    .doWork(LoadEventTodayListUseCase.Params("${MDate.date.value.toString().substringBefore(" ")}%", dao))
                var temp = 1440
                for (i in 0..11) {
                    listEventToday[i].timeSpent.postValue(listEventFromDb[i].timeSpent.value)
                    temp -= listEventFromDb[i].timeSpent.value ?: 0
                }
                _dayTimeInMinutes.postValue(temp)
                launchUi {
                    updatePieData()
                }
            }

        }
    }


    fun loadScheduledTimeFromDb() {
        launchIo {
            val dateForDb = MDate.date.value.toString().substringAfter("/").substringBefore(" ")
            val listScheduledTimeFromDb = loadScheduledTimeListUseCase
                .doWork(LoadScheduledTimeListUseCase.Params("%$dateForDb%", dao))
            listScheduledTimeFromDb.forEach {
                listEventScheduledTime[it.id].timeSchedule.postValue(
                    it.timeSchedule.value?.div(
                        MDate.maxDayInMonth
                    )
                )
            }
        }
    }


    fun openBottomSheet(id: Int) {
        val pressedButton = listParamsEvent[id]
        _tempEvent.postValue(
            pressedButton.name.value?.let { it1 ->
                BottomSheetParams(
                    color = Color.parseColor(pressedButton.color.value),
                    name = it1,
                    eventId = pressedButton.id,
                )
            }
        )
    }
}