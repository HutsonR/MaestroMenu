package com.example.databasexmlcourse.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.databasexmlcourse.data.database.AppDatabase
import com.example.databasexmlcourse.data.database.UserTypesDao
import com.example.databasexmlcourse.data.database.UsersDao
import com.example.databasexmlcourse.data.repository_api.UserTypesRepository
import com.example.databasexmlcourse.data.repository_api.UsersRepository
import com.example.databasexmlcourse.data.repository_impl.UserTypesRepositoryImpl
import com.example.databasexmlcourse.data.repository_impl.UsersRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
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
    fun provideUserTypesDao(appDatabase: AppDatabase): UserTypesDao {
        return appDatabase.userTypesDao
    }

    @Provides
    @Singleton
    fun provideUsersRepository(usersDao: UsersDao): UsersRepository {
        return UsersRepositoryImpl(usersDao)
    }

    @Provides
    @Singleton
    fun provideUserTypesRepository(userTypesDao: UserTypesDao): UserTypesRepository {
        return UserTypesRepositoryImpl(userTypesDao)
    }
}
