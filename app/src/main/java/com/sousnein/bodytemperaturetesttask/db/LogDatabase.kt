package com.sousnein.bodytemperaturetesttask.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = arrayOf(Log::class),
    version = 1
)
abstract class LogDatabase : RoomDatabase() {
    abstract fun getLogDao(): LogDao

    companion object {
        @Volatile
        private var instance: LogDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock = LOCK) {
            instance ?: buildDataBase(context).also {
                instance = it
            }
        }

        private fun buildDataBase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            LogDatabase::class.java,
            "log_table"
        ).build()
    }
}