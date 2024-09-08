package com.example.databasexmlcourse.features.feature_orders

import androidx.lifecycle.viewModelScope
import com.example.databasexmlcourse.core.BaseViewModel
import com.example.databasexmlcourse.domain.domain_api.TableStatusesUseCase
import com.example.databasexmlcourse.domain.domain_api.TablesUseCase
import com.example.databasexmlcourse.domain.models.DishItem
import com.example.databasexmlcourse.domain.models.OrdersItem
import com.example.databasexmlcourse.features.feature_orders.util.OrderStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val tablesUseCase: TablesUseCase,
    private val tableStatusesUseCase: TableStatusesUseCase
) : BaseViewModel<OrdersViewModel.State, OrdersViewModel.Actions>(State()) {

    init {
        viewModelScope.launch {
            modifyState { copy(isLoading = true) }

            val currentTime = System.currentTimeMillis()
            val fifteenMinutesEarlier = currentTime - 15 * 60 * 1000

            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val currentTimestamp = dateFormat.format(Date(currentTime))
            val earlierTimestamp = dateFormat.format(Date(fifteenMinutesEarlier))

            val list = listOf(
                OrdersItem(
                    id = "1",
                    userId = "1",
                    createdAt = currentTimestamp, // timestamp
                    tableId = "1",
                    dishes = listOf(
                        DishItem(
                            id = "1",
                            name = "Pizza",
                            price = 1000,
                            dishCategoryId = "1",
                            count = 2
                        ),
                        DishItem(
                            id = "2",
                            name = "Burger",
                            price = 500,
                            dishCategoryId = "1",
                            count = 1
                        ),
                        DishItem(
                            id = "3",
                            name = "Soup",
                            price = 200,
                            dishCategoryId = "1",
                            count = 3
                        )
                    ),
                    totalAmount = 100,
                    status = OrderStatus.AWAIT.status,
                    usersCount = 2
                ),
                OrdersItem(
                    id = "2",
                    userId = "2",
                    createdAt = earlierTimestamp, // timestamp
                    tableId = "2",
                    dishes = listOf(
                        DishItem(
                            id = "1",
                            name = "Pizza",
                            price = 1000,
                            dishCategoryId = "1",
                            count = 1
                        ),
                        DishItem(
                            id = "2",
                            name = "Burger",
                            price = 500,
                            dishCategoryId = "1",
                            count = 1
                        ),
                        DishItem(
                            id = "3",
                            name = "Soup",
                            price = 200,
                            dishCategoryId = "1",
                            count = 1
                        )
                    ),
                    totalAmount = 1250,
                    status = OrderStatus.IN_PROGRESS.status,
                    usersCount = 3
                )
            )
            modifyState {
                copy(
                    dataList = list,
                    isLoading = false
                )
            }
        }
    }

    fun onCancelClick(id: String) {
        val item = getState().dataList.find { it.id == id }
        item?.let {
            onAction(Actions.ShowCancelAlert(it))
        }
    }

    fun onConfirmCancelClick(item: OrdersItem) {
        // TODO - delete item
    }

    fun onActionClick(id: String) {
        val item = getState().dataList.find { it.id == id }
        when (item?.status) {
            OrderStatus.AWAIT.status -> {
                // TODO - update status
            }
            OrderStatus.IN_PROGRESS.status -> {
                // TODO - remove item and set status to DONE
            }
        }
    }

    fun openAddDialog() {
        onAction(Actions.OpenAddDialog)
    }

    data class State(
        val dataList: List<OrdersItem> = emptyList(),
        val isLoading: Boolean = true
    )

    sealed interface Actions {
        data object OpenAddDialog : Actions
        data class ShowCancelAlert(val item: OrdersItem) : Actions
    }

    companion object {
        const val TABLE_FREE = "Свободен"
        const val TABLE_BUSY = "Занят"
    }
}