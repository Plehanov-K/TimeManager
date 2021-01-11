package com.example.timemanager

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.shared.data.db.Db
import com.example.shared.data.db.events.entity.EventParams
import com.example.shared.data.entity.event.EventParamsUi
import com.example.timemanager.utils.launchIo

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val database = Db.getDb(this).eventDao()
    }
}
