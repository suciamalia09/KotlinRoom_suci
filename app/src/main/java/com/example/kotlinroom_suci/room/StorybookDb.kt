package com.example.kotlinroom_suci.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.locks.Lock

@Database(
    entities = [Storybook::class],
    version = 1
)
abstract class StorybookDb : RoomDatabase(){

    abstract fun storyDao() : StorybookDao

    companion object{
        @Volatile private var instance : StorybookDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also{
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            StorybookDb::class.java,
            "storybook09.db"
        ).build()
    }
}