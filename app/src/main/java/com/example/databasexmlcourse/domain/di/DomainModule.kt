package com.example.databasexmlcourse.domain.di

import com.example.databasexmlcourse.domain.domain_api.UserTypesUseCase
import com.example.databasexmlcourse.domain.domain_api.UsersUseCase
import com.example.databasexmlcourse.domain.domain_impl.UserTypesUseCaseImpl
import com.example.databasexmlcourse.domain.domain_impl.UsersUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun bindUsersUseCase(usersUseCaseImpl: UsersUseCaseImpl): UsersUseCase

    @Binds
    fun bindUserTypesUseCase(userTypesUseCaseImpl: UserTypesUseCaseImpl): UserTypesUseCase

}
