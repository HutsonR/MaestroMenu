package com.example.databasexmlcourse.data.di

import android.app.Application
import androidx.room.Room
import com.example.databasexmlcourse.data.database.AppDatabase
import com.example.databasexmlcourse.data.database.UsersDao
import com.example.databasexmlcourse.data.repository_api.UsersRepository
import com.example.databasexmlcourse.data.repository_impl.UsersRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideUsersDao(appDatabase: AppDatabase): UsersDao {
        return appDatabase.usersDao
    }

    @Provides
    @Singleton
    fun provideUsersRepository(usersDao: UsersDao): UsersRepository {
        return UsersRepositoryImpl(usersDao)
    }
}
