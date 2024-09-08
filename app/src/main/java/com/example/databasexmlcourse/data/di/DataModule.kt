package com.example.databasexmlcourse.data.di

import android.content.Context
import androidx.room.Room
import com.example.databasexmlcourse.data.database.AppDatabase
import com.example.databasexmlcourse.data.database.DishCategoriesDao
import com.example.databasexmlcourse.data.database.DishesDao
import com.example.databasexmlcourse.data.database.MIGRATION_1_2
import com.example.databasexmlcourse.data.database.MIGRATION_2_3
import com.example.databasexmlcourse.data.database.OrdersDao
import com.example.databasexmlcourse.data.database.TableStatusesDao
import com.example.databasexmlcourse.data.database.TablesDao
import com.example.databasexmlcourse.data.database.UserTypesDao
import com.example.databasexmlcourse.data.database.UsersDao
import com.example.databasexmlcourse.data.repository_api.DishCategoriesRepository
import com.example.databasexmlcourse.data.repository_api.DishesRepository
import com.example.databasexmlcourse.data.repository_api.OrdersRepository
import com.example.databasexmlcourse.data.repository_api.TableStatusesRepository
import com.example.databasexmlcourse.data.repository_api.TablesRepository
import com.example.databasexmlcourse.data.repository_api.UserTypesRepository
import com.example.databasexmlcourse.data.repository_api.UsersRepository
import com.example.databasexmlcourse.data.repository_impl.DishCategoriesRepositoryImpl
import com.example.databasexmlcourse.data.repository_impl.DishesRepositoryImpl
import com.example.databasexmlcourse.data.repository_impl.OrdersRepositoryImpl
import com.example.databasexmlcourse.data.repository_impl.TableStatusesRepositoryImpl
import com.example.databasexmlcourse.data.repository_impl.TablesRepositoryImpl
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
//            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
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
    fun provideDishesDao(appDatabase: AppDatabase): DishesDao {
        return appDatabase.dishesDao
    }

    @Provides
    @Singleton
    fun provideDishCategoriesDao(appDatabase: AppDatabase): DishCategoriesDao {
        return appDatabase.dishCategoriesDao
    }

    @Provides
    @Singleton
    fun provideOrders(appDatabase: AppDatabase): OrdersDao {
        return appDatabase.ordersDao
    }

    @Provides
    @Singleton
    fun provideTablesDao(appDatabase: AppDatabase): TablesDao {
        return appDatabase.tablesDao
    }

    @Provides
    @Singleton
    fun provideTableStatusesDao(appDatabase: AppDatabase): TableStatusesDao {
        return appDatabase.tableStatusesDao
    }


    //
    //  Repository
    //
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

    @Provides
    @Singleton
    fun provideDishesRepository(dishesDao: DishesDao): DishesRepository {
        return DishesRepositoryImpl(dishesDao)
    }

    @Provides
    @Singleton
    fun provideDishCategoriesRepository(dishCategoriesDao: DishCategoriesDao): DishCategoriesRepository {
        return DishCategoriesRepositoryImpl(dishCategoriesDao)
    }

    @Provides
    @Singleton
    fun provideTablesRepository(tablesDao: TablesDao): TablesRepository {
        return TablesRepositoryImpl(tablesDao)
    }

    @Provides
    @Singleton
    fun provideTableStatusRepository(tableStatusesDao: TableStatusesDao): TableStatusesRepository {
        return TableStatusesRepositoryImpl(tableStatusesDao)
    }

    @Provides
    @Singleton
    fun provideOrdersRepository(ordersDao: OrdersDao, dishesDao: DishesDao): OrdersRepository {
        return OrdersRepositoryImpl(ordersDao, dishesDao)
    }
}
