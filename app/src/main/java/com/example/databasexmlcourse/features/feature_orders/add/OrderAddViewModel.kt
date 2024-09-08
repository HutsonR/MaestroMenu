package com.example.databasexmlcourse.features.feature_orders.add

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.databasexmlcourse.core.BaseViewModel
import com.example.databasexmlcourse.domain.domain_api.DishCategoriesUseCase
import com.example.databasexmlcourse.domain.domain_api.DishesUseCase
import com.example.databasexmlcourse.domain.domain_api.TableStatusesUseCase
import com.example.databasexmlcourse.domain.domain_api.TablesUseCase
import com.example.databasexmlcourse.domain.models.DishCategory
import com.example.databasexmlcourse.domain.models.DishItem
import com.example.databasexmlcourse.domain.models.OrdersItem
import com.example.databasexmlcourse.domain.models.TableItem
import com.example.databasexmlcourse.domain.models.TableStatus
import com.example.databasexmlcourse.features.common.dialogs.adapter.models.DialogSearcherModel
import com.example.databasexmlcourse.features.feature_orders.OrdersViewModel.Companion.TABLE_FREE
import com.example.databasexmlcourse.features.feature_orders.util.OrderStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OrderAddViewModel @Inject constructor(
    private val tablesUseCase: TablesUseCase,
    private val tableStatusesUseCase: TableStatusesUseCase,
    private val dishesUseCase: DishesUseCase,
    private val dishCategoriesUseCase: DishCategoriesUseCase
) : BaseViewModel<OrderAddViewModel.State, OrderAddViewModel.Actions>(State()) {

    private var allDishes: List<DishItem> = emptyList()

    init {
        updateDishList()
    }

    fun onTableButtonClick() {
        onAction(Actions.OpenTableDialog)
    }

    fun onCategoryButtonClick() {
        onAction(Actions.OpenCategoryDialog)
    }

    fun goBack() {
        onAction(Actions.GoBack)
    }

    fun getCategories(): List<DialogSearcherModel> = runBlocking {
        withContext(Dispatchers.IO) {
            val categories = dishCategoriesUseCase.getAll()
            modifyState { copy(dishCategories = categories) }
            categories.map { DialogSearcherModel(it.id, it.name) }
        }
    }

    fun getTables(): List<DialogSearcherModel> = runBlocking {
        withContext(Dispatchers.IO) {
            val table = tablesUseCase.getAll()
            val tableStatuses = tableStatusesUseCase.getAll().filter { it.name == TABLE_FREE }
            val filteredTable = table.filter { it.tableStatusId == tableStatuses.first().id }.sortedBy { it.number }

            modifyState { copy(tables = filteredTable) }
            filteredTable.map { DialogSearcherModel(it.id, it.number.toString()) }
        }
    }

    fun updateCategory(id: String) = viewModelScope.launch {
        val category = getState().dishCategories.find { it.id == id }
        category?.let {
            modifyState {
                copy(currentDishCategory = it)
            }
            updateDishList()
        }
        updateSaveButtonState()
    }

    fun updateTable(id: String) = viewModelScope.launch {
        val table = getState().tables.find { it.id == id }
        table?.let {
            modifyState {
                copy(currentTable = it)
            }
        }
        updateSaveButtonState()
    }

    private fun updateDishList() = viewModelScope.launch {
        modifyState { copy(isLoading = true) }

        if (allDishes.isEmpty()) {
            val fetchedDishes = async { dishesUseCase.getAll() }
            allDishes = fetchedDishes.await()
        }

        val currentCategoryId = getState().currentDishCategory?.id
        val list: List<DishItem> = allDishes.filter { it.dishCategoryId == currentCategoryId }
        modifyState {
            copy(
                isLoading = false,
                dishes = list
            )
        }
    }

    private fun modifyList(itemId: String, isAdd: Boolean) = viewModelScope.launch {
        modifyState { copy(isLoading = true) }

        allDishes = allDishes.map { if (it.id == itemId) it.copy(count = if (isAdd) it.count + 1 else it.count - 1) else it }
        val filteredList: List<DishItem> = allDishes.filter { it.dishCategoryId == getState().currentDishCategory?.id }

        modifyState {
            copy(
                isLoading = false,
                dishes = filteredList
            )
        }
    }

    fun onAddDishClick(id: String) {
        modifyList(id, true)
        val list = getState().dishes

        val currentOrder = getState().currentOrder ?: initOrderItem()
        val existingDish = currentOrder.dishes.find { it.id == id }

        val updatedDishes = if (existingDish != null) {
            currentOrder.dishes.map {
                if (it.id == id) it.copy(count = it.count + 1) else it
            }
        } else {
            val newDish = list.find { it.id == id }?.copy(count = 1)
            if (newDish != null) currentOrder.dishes.plus(newDish) else currentOrder.dishes
        }

        modifyState {
            copy(
                currentOrder = currentOrder.copy(
                    dishes = updatedDishes,
                    totalAmount = sumDishList()
                )
            )
        }
    }

    fun onRemoveDishClick(id: String) {
        modifyList(id, false)

        val currentOrder = getState().currentOrder
        val existingDish = currentOrder?.dishes?.find { it.id == id }

        val updatedDishes = (if (existingDish != null) {
            currentOrder.dishes.map {
                if (it.id == id) it.copy(count = it.count - 1) else it
            }
        } else {
            currentOrder?.dishes
        })?.filter { it.count > 0 }

        modifyState {
            copy(
                currentOrder = currentOrder?.copy(
                    dishes = updatedDishes ?: emptyList(),
                    totalAmount = sumDishList()
                )
            )
        }
    }

    private fun sumDishList(): Int {
        var sum = 0
        getState().dishes.forEach {
            sum += it.price * it.count
        }
        return sum
    }

    private fun updateSaveButtonState() {
        val isEnable = getState().currentTable != null
                && getState().currentDishCategory != null
                && getState().currentOrder?.dishes?.isNotEmpty() ?: false
        modifyState { copy(isSaveButtonEnable = isEnable) }
    }

    fun onActionButtonClick() = viewModelScope.launch {

        // TODO Добавить заказ в БД и закрыть окно
    }

    private fun initOrderItem() = OrdersItem(
        id = "",
        userId = "",
        createdAt = "",
        tableId = getState().currentTable?.id ?: "",
        dishes = emptyList(),
        totalAmount = 0,
        status = OrderStatus.AWAIT.status,
        usersCount = 0
    )

    data class State(
        val currentOrder: OrdersItem? = null,
        val currentTable: TableItem? = null,
        val currentDishCategory: DishCategory? = null,
        val dishCategories: List<DishCategory> = emptyList(),
        val tables: List<TableItem> = emptyList(),
        val dishes: List<DishItem> = emptyList(),
        val isLoading: Boolean = false,
        val isSaveButtonEnable: Boolean = false
    )

    sealed interface Actions {
        data object GoBack : Actions
        data object OpenTableDialog : Actions
        data object OpenCategoryDialog : Actions
    }
}