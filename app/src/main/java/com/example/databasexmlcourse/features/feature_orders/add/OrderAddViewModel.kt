package com.example.databasexmlcourse.features.feature_orders.add

import android.util.Log
import com.example.databasexmlcourse.core.BaseViewModel
import com.example.databasexmlcourse.domain.models.DishItem
import com.example.databasexmlcourse.domain.models.OrdersItem
import com.example.databasexmlcourse.features.common.dialogs.adapter.models.DialogSearcherModel
import com.example.databasexmlcourse.features.feature_orders.util.OrderStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderAddViewModel @Inject constructor(
    // UseCase
) : BaseViewModel<OrderAddViewModel.State, OrderAddViewModel.Actions>(State()) {

    var categoryList = listOf(
        DialogSearcherModel("1", "Все"),
        DialogSearcherModel("2", "Завтрак"),
        DialogSearcherModel("3", "Обед"),
        DialogSearcherModel("4", "Ужин")
    )

    var tableList = listOf(
        DialogSearcherModel("1", "1 столик"),
        DialogSearcherModel("2", "2 столик"),
        DialogSearcherModel("3", "3 столик"),
        DialogSearcherModel("4", "4 столик"),
        DialogSearcherModel("5", "5 столик"),
        DialogSearcherModel("6", "6 столик"),
        DialogSearcherModel("7", "7 столик"),
        DialogSearcherModel("8", "8 столик"),
        DialogSearcherModel("9", "9 столик"),
        DialogSearcherModel("10", "10 столик")
    )

    init {
        val dishes = listOf(
            DishItem("1", "Борщ", 100, "25", 0),
            DishItem("2", "Суп", 120, "25", 0),
        )
        modifyState { copy(dishList = dishes) }
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

    fun updateCategory(text: String) {
        modifyState { copy(category = text) }
        if (getState().table.isNotBlank() && getState().category.isNotBlank()) {
            updateDishList()
        }
        updateSaveButtonState()
    }

    private fun updateDishList() {
        // TODO - update dish list
        val list: List<DishItem> = listOf()
        modifyState { copy(dishList = list) }
    }

    fun onAddDishClick(id: String) {
        val modifiedList = getState().dishList.map { if (it.id == id) it.copy(count = it.count + 1) else it }
        modifyState {
            copy(dishList = modifiedList)
        }

        val item = getState().dishList.find { it.id == id } ?: return
        val currentOrder = getState().currentOrder ?: initOrderItem()
        modifyState {
            copy(
                currentOrder = currentOrder.copy(
                    dishes = currentOrder.dishes.plus(item),
                    totalAmount = sumDishList()
                )
            )
        }
        Log.d("debugTag", "onAddDishClick: ${getState().currentOrder}")
    }

    fun onRemoveDishClick(id: String) {
        val modifiedList = getState().dishList.map { if (it.id == id && it.count > 0) it.copy(count = it.count - 1) else it }
        modifyState {
            copy(dishList = modifiedList)
        }

        val item = getState().dishList.find { it.id == id } ?: return
        val currentOrder = getState().currentOrder
        modifyState {
            copy(
                currentOrder = currentOrder?.copy(
                    dishes = currentOrder.dishes.minus(item),
                    totalAmount = sumDishList()
                )
            )
        }
        Log.d("debugTag", "onRemoveDishClick: ${getState().currentOrder}")
    }

    private fun sumDishList(): Int {
        var sum = 0
        getState().dishList.forEach {
            sum += it.price * it.count
        }
        return sum
    }

    private fun updateSaveButtonState() {
        val isEnable = getState().table.isNotBlank() && getState().category.isNotBlank()
        modifyState { copy(isSaveButtonEnable = isEnable) }
    }

    fun onActionButtonClick() {
        // TODO Добавить заказ в БД и закрыть окно
    }

    private fun initOrderItem() = OrdersItem(
        id = "",
        userId = "",
        createdAt = "",
        tableId = getState().table,
        dishes = emptyList(),
        totalAmount = 0,
        status = OrderStatus.AWAIT.status,
        usersCount = 0
    )

    data class State(
        val table: String = "",
        val category: String = "",
        val currentOrder: OrdersItem? = null,
        val dishList: List<DishItem> = emptyList(),
        val isLoading: Boolean = false,
        val isSaveButtonEnable: Boolean = false
    )

    sealed interface Actions {
        data object GoBack : Actions
        data object OpenTableDialog : Actions
        data object OpenCategoryDialog : Actions
    }

}