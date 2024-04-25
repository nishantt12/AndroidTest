package com.androidtest.presistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single

@Dao
interface MainDao {


    @Query("Select * from MainModelEntity")
    fun getTalaList(): Single<List<MainModelEntity>>

    @Insert
    fun insertTalaList(list: List<MainModelEntity>)

}