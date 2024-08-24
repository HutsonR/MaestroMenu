package com.example.databasexmlcourse.data.repository_impl

import com.example.databasexmlcourse.data.database.DishesDao
import com.example.databasexmlcourse.data.models.asEntity
import com.example.databasexmlcourse.data.models.asExternalModel
import com.example.databasexmlcourse.data.repository_api.DishesRepository
import com.example.databasexmlcourse.domain.models.DishItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DishesRepositoryImpl @Inject constructor (
    private val dishesDao: DishesDao
): DishesRepository {

    override suspend fun insert(item: DishItem) = withContext(Dispatchers.IO) {
        dishesDao.insert(item.asEntity())
    }

    override suspend fun getDishById(userId: String): DishItem? = withContext(Dispatchers.IO) {
        val userEntity = dishesDao.getDishById(userId)
        userEntity?.asExternalModel()
    }

    override suspend fun getAll(): List<DishItem> = withContext(Dispatchers.IO) {
        val usersEntities = dishesDao.getAll()
        usersEntities.map { it.asExternalModel() }
    }

    override suspend fun deleteById(itemId: String) = withContext(Dispatchers.IO) {
        dishesDao.deleteById(itemId)
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        dishesDao.deleteAll()
    }

    override suspend fun update(item: DishItem) = withContext(Dispatchers.IO) {
        dishesDao.update(item.asEntity())
    }

}