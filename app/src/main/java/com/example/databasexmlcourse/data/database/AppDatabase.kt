package com.example.databasexmlcourse.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.databasexmlcourse.data.models.UsersEntity

@Database(entities = [UsersEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val usersDao: UsersDao

    companion object {
        const val DATABASE_NAME = "rest_database"
    }
}
