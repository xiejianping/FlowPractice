package com.example.flowpractice.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM user ")
    fun getAll(): Flow<List<User>>
}