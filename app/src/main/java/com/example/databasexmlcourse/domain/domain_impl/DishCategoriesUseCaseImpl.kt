package com.example.databasexmlcourse.domain.domain_impl

import com.example.databasexmlcourse.data.repository_api.DishCategoriesRepository
import com.example.databasexmlcourse.data.repository_api.UserTypesRepository
import com.example.databasexmlcourse.domain.domain_api.DishCategoriesUseCase
import com.example.databasexmlcourse.domain.domain_api.UserTypesUseCase
import com.example.databasexmlcourse.domain.models.DishCategory
import com.example.databasexmlcourse.domain.models.UserType
import com.example.databasexmlcourse.domain.util.Resource
import javax.inject.Inject

class DishCategoriesUseCaseImpl @Inject constructor (
    private val dishCategoriesRepository: DishCategoriesRepository
): DishCategoriesUseCase {
    override suspend fun insert(item: DishCategory): Resource {
        return if (item.name.isEmpty())
            Resource.Failed(Exception("Fields can not be empty"))
        else {
            Resource.Success(dishCategoriesRepository.insert(item))
        }
    }

    override suspend fun getDishCategoryById(itemId: String): Resource {
        val type = dishCategoriesRepository.getDishCategoryById(itemId)
        return if (type != null) {
            Resource.Success(type)
        } else {
            Resource.Failed(Exception("Dish category not found"))
        }
    }

    override suspend fun getAll(): List<DishCategory> {
        return dishCategoriesRepository.getAll()
    }

    override suspend fun deleteById(itemId: String): Resource =
        Resource.Success(dishCategoriesRepository.deleteById(itemId))

    override suspend fun deleteAll() =
        dishCategoriesRepository.deleteAll()

}