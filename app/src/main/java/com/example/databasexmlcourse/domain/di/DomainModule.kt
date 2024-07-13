package com.example.databasexmlcourse.domain.di

import com.example.databasexmlcourse.data.database.UsersDao
import com.example.databasexmlcourse.data.repository_api.UsersRepository
import com.example.databasexmlcourse.data.repository_impl.UsersRepositoryImpl
import com.example.databasexmlcourse.domain.domain_api.UsersUseCase
import com.example.databasexmlcourse.domain.domain_impl.UsersUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun provideUsersUseCase(usersRepository: UsersRepository): UsersUseCase {
        return UsersUseCaseImpl(usersRepository)
    }
}
