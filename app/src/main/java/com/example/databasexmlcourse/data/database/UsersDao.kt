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

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun checkUser(username: String, password: String): UsersEntity?

    @Query("SELECT COUNT(*) > 0 FROM users WHERE id = :userId")
    suspend fun checkUserById(userId: String): Boolean

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): UsersEntity?

    @Query("SELECT * FROM users ORDER BY id DESC")
    suspend fun getAll(): List<UsersEntity>

    @Query("DELETE FROM users WHERE id = :itemId")
    suspend fun deleteById(itemId: String)

    @Query("DELETE FROM users")
    suspend fun deleteAll()

    @Update
    suspend fun update(item: UsersEntity)
}
