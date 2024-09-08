package com.example.databasexmlcourse.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.databasexmlcourse.data.models.DishCategoriesEntity
import com.example.databasexmlcourse.data.models.DishesEntity
import com.example.databasexmlcourse.data.models.OrderDishesEntity
import com.example.databasexmlcourse.data.models.OrdersEntity
import com.example.databasexmlcourse.data.models.TableStatusesEntity
import com.example.databasexmlcourse.data.models.TablesEntity
import com.example.databasexmlcourse.data.models.UsersEntity
import com.example.databasexmlcourse.data.models.UsersTypesEntity

@Database(
    entities = [
        UsersEntity::class,
        UsersTypesEntity::class,
        DishesEntity::class,
        DishCategoriesEntity::class,
        OrdersEntity::class,
        OrderDishesEntity::class, // TODO ??
        TablesEntity::class, // TODO ??
        TableStatusesEntity::class
    ],
    version = 4
)
abstract class AppDatabase : RoomDatabase() {
    abstract val usersDao: UsersDao
    abstract val userTypesDao: UserTypesDao
    abstract val dishesDao: DishesDao
    abstract val dishCategoriesDao: DishCategoriesDao
    abstract val ordersDao: OrdersDao
    abstract val tablesDao: TablesDao
    abstract val tableStatusesDao: TableStatusesDao

    companion object {
        const val DATABASE_NAME = "rest_database"
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Создание новой таблицы для DishesEntity
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `dishes` (
                `id` TEXT NOT NULL,
                `name` TEXT NOT NULL,
                `price` INTEGER NOT NULL,
                `dishCategoryId` TEXT NOT NULL,
                `count` INTEGER NOT NULL,
                PRIMARY KEY(`id`),
                FOREIGN KEY(`dishCategoryId`) REFERENCES `dish_categories`(`id`) ON DELETE CASCADE
            )
            """.trimIndent()
        )

        // Создание новой таблицы для DishCategoriesEntity
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `dish_categories` (
                `id` TEXT NOT NULL,
                `name` TEXT NOT NULL,
                PRIMARY KEY(`id`)
            )
            """.trimIndent()
        )

        // Создание индекса для столбца dishCategoryId в таблице dishes
        database.execSQL(
            "CREATE INDEX IF NOT EXISTS `index_dishes_dishCategoryId` ON `dishes` (`dishCategoryId`)"
        )
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Создание таблицы для TableStatusesEntity
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS table_statuses (
                id TEXT NOT NULL,
                name TEXT NOT NULL,
                PRIMARY KEY(id)
            )
            """.trimIndent()
        )

        // Создание таблицы для TablesEntity
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS tables (
                id TEXT NOT NULL,
                number INTEGER NOT NULL,
                tableStatusId TEXT NOT NULL,
                PRIMARY KEY(id),
                FOREIGN KEY(tableStatusId) REFERENCES table_statuses(id) ON DELETE CASCADE
            )
            """.trimIndent()
        )

        // Создание индекса для столбца tableStatusId в таблице tables
        database.execSQL(
            "CREATE INDEX IF NOT EXISTS index_tables_tableStatusId ON tables (tableStatusId)"
        )
    }
}
