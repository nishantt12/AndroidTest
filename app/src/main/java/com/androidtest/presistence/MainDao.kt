package com.androidtest.presistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface MainDao {


    @Query("Select * from MainModelEntity")
    fun getTalaList(): Maybe<List<MainModelEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTalaList(list: List<MainModelEntity>)

}