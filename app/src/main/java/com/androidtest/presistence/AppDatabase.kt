package com.androidtest.presistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androidtest.model.MainModel


@Database(entities = [MainModelEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun mainDao(): MainDao

}