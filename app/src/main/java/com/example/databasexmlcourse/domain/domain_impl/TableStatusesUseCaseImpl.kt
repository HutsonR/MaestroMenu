package com.example.databasexmlcourse.domain.domain_impl

import com.example.databasexmlcourse.data.repository_api.TableStatusesRepository
import com.example.databasexmlcourse.domain.domain_api.TableStatusesUseCase
import com.example.databasexmlcourse.domain.models.TableStatus
import com.example.databasexmlcourse.domain.util.Resource
import javax.inject.Inject

class TableStatusesUseCaseImpl @Inject constructor (
    private val tableStatusesRepository: TableStatusesRepository
): TableStatusesUseCase {
    override suspend fun insert(item: TableStatus): Resource {
        return if (item.name.isEmpty())
            Resource.Failed(Exception("Fields can not be empty"))
        else {
            Resource.Success(tableStatusesRepository.insert(item))
        }
    }

    override suspend fun getDishCategoryById(itemId: String): Resource {
        val item = tableStatusesRepository.getTableStatusById(itemId)
        return if (item != null) {
            Resource.Success(item)
        } else {
            Resource.Failed(Exception("Dish category not found"))
        }
    }

    override suspend fun getAll(): List<TableStatus> {
        return tableStatusesRepository.getAll()
    }

    override suspend fun deleteById(itemId: String): Resource =
        Resource.Success(tableStatusesRepository.deleteById(itemId))

    override suspend fun deleteAll() =
        tableStatusesRepository.deleteAll()

}