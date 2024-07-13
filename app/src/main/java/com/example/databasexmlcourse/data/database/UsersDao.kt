package com.example.databasexmlcourse.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.databasexmlcourse.data.models.UsersEntity

@Dao
interface UsersDao {
    @Insert
    suspend fun insert(item: UsersEntity)

    @Query("SELECT * FROM users ORDER BY id DESC")
    fun getAll(): List<UsersEntity>

    @Query("DELETE FROM users WHERE id = :itemId")
    suspend fun deleteById(itemId: Int)

    @Query("DELETE FROM users")
    suspend fun deleteAll()

    @Update
    fun update(item: UsersEntity)
}
