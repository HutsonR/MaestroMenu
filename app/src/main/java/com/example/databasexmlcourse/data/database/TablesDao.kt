package com.example.databasexmlcourse.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.databasexmlcourse.data.models.TablesEntity

@Dao
interface TablesDao {
    @Insert
    suspend fun insert(item: TablesEntity)

    @Query("SELECT * FROM tables WHERE id = :userId")
    suspend fun getTableById(userId: String): TablesEntity?

    @Query("SELECT * FROM tables ORDER BY id DESC")
    suspend fun getAll(): List<TablesEntity>

    @Query("DELETE FROM tables WHERE id = :itemId")
    suspend fun deleteById(itemId: String)

    @Query("DELETE FROM tables")
    suspend fun deleteAll()

    @Update
    suspend fun update(item: TablesEntity)
}
