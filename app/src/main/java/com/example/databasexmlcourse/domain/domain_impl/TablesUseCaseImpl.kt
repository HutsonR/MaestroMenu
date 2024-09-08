package com.example.databasexmlcourse.domain.domain_impl

import com.example.databasexmlcourse.data.repository_api.TablesRepository
import com.example.databasexmlcourse.domain.domain_api.TablesUseCase
import com.example.databasexmlcourse.domain.models.TableItem
import com.example.databasexmlcourse.domain.util.Resource
import javax.inject.Inject

class TablesUseCaseImpl @Inject constructor (
    private val tablesRepository: TablesRepository
): TablesUseCase {
    override suspend fun insert(item: TableItem): Resource {
        return if (item.number == 0 || item.tableStatusId.isEmpty())
            Resource.Failed(Exception("Fields can not be empty"))
        else {
            Resource.Success(tablesRepository.insert(item))
        }
    }

    override suspend fun getTableById(id: String): Resource {
        val item = tablesRepository.getTableById(id)
        return if (item != null) {
            Resource.Success(item)
        } else {
            Resource.Failed(Exception("Dish not found"))
        }
    }

    override suspend fun getAll(): List<TableItem> {
        return tablesRepository.getAll()
    }

    override suspend fun deleteById(itemId: String): Resource =
        Resource.Success(tablesRepository.deleteById(itemId))

    override suspend fun deleteAll() =
        tablesRepository.deleteAll()

    override suspend fun update(item: TableItem): Resource =
        Resource.Success(tablesRepository.update(item))

}