package com.maximvs.trackingtravel.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maximvs.trackingtravel.data.entity.Route
import kotlinx.coroutines.flow.Flow

@Dao
interface RouteDao {
    @Query("SELECT * FROM cached_routes")
    fun getCachedRoutes(): Flow<List<Route>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Route>)

}
