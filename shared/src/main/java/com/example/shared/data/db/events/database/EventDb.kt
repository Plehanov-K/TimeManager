package com.example.shared.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.shared.data.db.events.dao.EventDao
import com.example.shared.data.db.events.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


@Database(
    entities = arrayOf(EventToday::class, EventParams::class, ScheduledTime::class),
    version = 1
)
abstract class EventDb : RoomDatabase() {

    abstract fun eventDao(): EventDao
}

object Db {
    fun getDb(ctx: Context) =
        Room.databaseBuilder(ctx, EventDb::class.java, "EventDataBase")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).let {
                        Event.list.forEach {
                            db.execSQL("INSERT INTO " + TABLE_PARAMS_NAME +" ("
                                    + COLUMN_PARAMS_ID + ", " + COLUMN_PARAMS_NAME +", "
                                    + COLUMN_PARAMS_COLOR + ") VALUES (${it.eventId}, '${it.eventName}', '${it.eventColor}');")
                        }
                    }

                }
            }).build()
}

private object Event {
    val list = listOf<EventParams>(
        EventParams(0,"Sleep", "#304FFE"),
        EventParams(1,"Study", "#9C27B0"),
        EventParams(2,"Reading", "#4CAF50"),
        EventParams(3,"Сooking", "#FFEB3B"),
        EventParams(4,"Сleaning", "#F44336"),
        EventParams(5,"Watch TV" ,"#E91E63"),
        EventParams(6,"Time with friends", "#009688"),
        EventParams(7,"Time with family", "#FFBB86FC"),
        EventParams(8,"Work", "#00E5FF"),
        EventParams(9,"Get to work.", "#C6FF00"),
        EventParams(10,"Shopping",  "#00E676"),
        EventParams(11,"Programming",  "#FF6200EE")
    )
}
