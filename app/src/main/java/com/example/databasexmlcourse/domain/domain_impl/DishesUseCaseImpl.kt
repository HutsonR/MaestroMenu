package com.example.databasexmlcourse.domain.domain_impl

import com.example.databasexmlcourse.data.repository_api.DishesRepository
import com.example.databasexmlcourse.domain.domain_api.DishesUseCase
import com.example.databasexmlcourse.domain.models.DishItem
import com.example.databasexmlcourse.domain.util.Resource
import javax.inject.Inject

class DishesUseCaseImpl @Inject constructor (
    private val dishesRepository: DishesRepository
): DishesUseCase {
    override suspend fun insert(item: DishItem): Resource {
        return if (item.name.isEmpty() || item.price <= 0 || item.dishCategoryId.isEmpty())
            Resource.Failed(Exception("Fields can not be empty"))
        else {
            Resource.Success(dishesRepository.insert(item))
        }
    }

    override suspend fun getDishById(id: String): Resource {
        val item = dishesRepository.getDishById(id)
        return if (item != null) {
            Resource.Success(item)
        } else {
            Resource.Failed(Exception("Dish not found"))
        }
    }

    override suspend fun getAll(): List<DishItem> {
        return dishesRepository.getAll()
    }

    override suspend fun deleteById(itemId: String): Resource =
        Resource.Success(dishesRepository.deleteById(itemId))

    override suspend fun deleteAll() =
        dishesRepository.deleteAll()

    override suspend fun update(item: DishItem): Resource =
        Resource.Success(dishesRepository.update(item))

}