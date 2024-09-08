package com.example.databasexmlcourse.domain.di

import com.example.databasexmlcourse.domain.domain_api.DishCategoriesUseCase
import com.example.databasexmlcourse.domain.domain_api.DishesUseCase
import com.example.databasexmlcourse.domain.domain_api.OrdersUseCase
import com.example.databasexmlcourse.domain.domain_api.TableStatusesUseCase
import com.example.databasexmlcourse.domain.domain_api.TablesUseCase
import com.example.databasexmlcourse.domain.domain_api.UserTypesUseCase
import com.example.databasexmlcourse.domain.domain_api.UsersUseCase
import com.example.databasexmlcourse.domain.domain_impl.DishCategoriesUseCaseImpl
import com.example.databasexmlcourse.domain.domain_impl.DishesUseCaseImpl
import com.example.databasexmlcourse.domain.domain_impl.OrdersUseCaseImpl
import com.example.databasexmlcourse.domain.domain_impl.TableStatusesUseCaseImpl
import com.example.databasexmlcourse.domain.domain_impl.TablesUseCaseImpl
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

    @Binds
    fun bindDishesUseCase(dishesUseCaseImpl: DishesUseCaseImpl): DishesUseCase

    @Binds
    fun bindDishCategoriesUseCase(dishCategoriesUseCaseImpl: DishCategoriesUseCaseImpl): DishCategoriesUseCase

    @Binds
    fun bindTablesUseCase(tablesUseCaseImpl: TablesUseCaseImpl): TablesUseCase

    @Binds
    fun bindTableStatusesUseCase(tableStatusesUseCaseImpl: TableStatusesUseCaseImpl): TableStatusesUseCase

    @Binds
    fun bindOrdersUseCase(ordersUseCaseImpl: OrdersUseCaseImpl): OrdersUseCase
}
