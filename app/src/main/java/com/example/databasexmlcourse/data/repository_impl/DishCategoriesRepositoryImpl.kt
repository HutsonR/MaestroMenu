package com.example.databasexmlcourse.data.repository_impl

import com.example.databasexmlcourse.data.database.DishCategoriesDao
import com.example.databasexmlcourse.data.database.UserTypesDao
import com.example.databasexmlcourse.data.models.asEntity
import com.example.databasexmlcourse.data.models.asExternalModel
import com.example.databasexmlcourse.data.repository_api.DishCategoriesRepository
import com.example.databasexmlcourse.data.repository_api.UserTypesRepository
import com.example.databasexmlcourse.domain.models.DishCategory
import com.example.databasexmlcourse.domain.models.UserType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DishCategoriesRepositoryImpl @Inject constructor (
    private val dishCategoriesDao: DishCategoriesDao
): DishCategoriesRepository {

    override suspend fun insert(item: DishCategory) = withContext(Dispatchers.IO) {
        dishCategoriesDao.insert(item.asEntity())
    }

    override suspend fun getDishCategoryById(itemId: String): DishCategory? = withContext(Dispatchers.IO) {
        val userTypeEntity = dishCategoriesDao.getDishCategoryById(itemId)
        userTypeEntity?.asExternalModel()
    }

    override suspend fun getAll(): List<DishCategory> = withContext(Dispatchers.IO) {
        val userTypesEntities = dishCategoriesDao.getAll()
        userTypesEntities.map { it.asExternalModel() }
    }

    override suspend fun deleteById(itemId: String) = withContext(Dispatchers.IO) {
        dishCategoriesDao.deleteById(itemId)
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        dishCategoriesDao.deleteAll()
    }

}