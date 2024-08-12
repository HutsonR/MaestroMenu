package com.example.databasexmlcourse.features.feature_menu

import androidx.lifecycle.viewModelScope
import com.example.databasexmlcourse.core.BaseViewModel
import com.example.databasexmlcourse.domain.models.DishItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor() : BaseViewModel<MenuViewModel.State, MenuViewModel.Actions>(State()) {

    fun openDialog() {
        onAction(Actions.OpenAddDishDialog)
    }

    private var jobChangeQuerySearch: Job? = null
    private var list: List<DishItem> = emptyList()

    init {
        viewModelScope.launch {
            modifyState { copy(isLoading = true) }

            list = listOf(
                DishItem(id = "1", name = "Бургер", price = 250, dishCategoryId = "55", count = 0),
                DishItem(id = "2", name = "Пицца Маргаритта", price = 449, dishCategoryId = "2", count = 0)
            )
            modifyState {
                copy(
                    dataList = list,
                    isLoading = false
                )
            }
        }
    }

    fun onEditClick(id: String) {
        val item = getState().dataList.find { it.id == id }
        item?.let {
            onAction(Actions.OpenEditDishDialog(it))
        }
    }

    fun onChangeQuerySearch(querySearch: String) {
        jobChangeQuerySearch?.cancel()
        jobChangeQuerySearch = viewModelScope.launch {
            delay(DELAY_QUERY_SEARCH)
            val filteredList = filterItemsByName(list, querySearch)
            modifyState {
                copy(
                    dataList = filteredList,
                    query = querySearch
                )
            }
        }
    }

    private fun filterItemsByName(
        list: List<DishItem>,
        querySearch: String
    ): List<DishItem> {
        return if (querySearch.isEmpty()) {
            list
        } else {
            list.filter {
                it.name.contains(querySearch, ignoreCase = true)
            }
        }
    }

    data class State(
        val dataList: List<DishItem> = emptyList(),
        val query: String = "",
        val isLoading: Boolean = true
    )

    sealed interface Actions {
        data object OpenAddDishDialog : Actions
        data class OpenEditDishDialog(val item: DishItem) : Actions
    }

    companion object {
        private const val DELAY_QUERY_SEARCH = 200L
    }

}