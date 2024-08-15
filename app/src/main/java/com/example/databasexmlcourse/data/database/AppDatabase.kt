package com.example.databasexmlcourse.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.databasexmlcourse.data.models.UsersEntity
import com.example.databasexmlcourse.data.models.UsersTypesEntity

@Database(entities = [UsersEntity::class, UsersTypesEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val usersDao: UsersDao
    abstract val userTypesDao: UserTypesDao

    companion object {
        const val DATABASE_NAME = "rest_database"
    }
}
