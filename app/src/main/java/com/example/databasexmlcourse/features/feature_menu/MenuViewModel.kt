package com.example.databasexmlcourse.features.feature_menu

import androidx.lifecycle.viewModelScope
import com.example.databasexmlcourse.core.BaseViewModel
import com.example.databasexmlcourse.domain.domain_api.DishCategoriesUseCase
import com.example.databasexmlcourse.domain.domain_api.DishesUseCase
import com.example.databasexmlcourse.domain.models.DishCategory
import com.example.databasexmlcourse.domain.models.DishCompositeItem
import com.example.databasexmlcourse.domain.models.DishItem
import com.example.databasexmlcourse.domain.models.PersonalItem
import com.example.databasexmlcourse.domain.models.UserType
import com.example.databasexmlcourse.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val dishesUseCase: DishesUseCase,
    private val dishCategoriesUseCase: DishCategoriesUseCase
) : BaseViewModel<MenuViewModel.State, MenuViewModel.Actions>(State()) {

    fun openDialog() {
        onAction(Actions.OpenAddDishDialog)
    }

    private var jobChangeQuerySearch: Job? = null
    private var list: MutableList<DishCompositeItem> = mutableListOf()

    init {
        updateList()
    }

    fun updateList() {
        viewModelScope.launch {
            modifyState {
                copy(
                    dataList = emptyList(),
                    isLoading = true
                )
            }
            list.clear()
            val dishes = dishesUseCase.getAll()
            dishes.forEach { dish ->
                when (val categoryResponse = dishCategoriesUseCase.getDishCategoryById(dish.dishCategoryId)) {
                    is Resource.Success<*> -> {
                        val category = (categoryResponse.data as DishCategory)
                        list.add(
                            DishCompositeItem(
                                id = dish.id,
                                name = dish.name,
                                price = dish.price,
                                category = category,
                                count = 0
                            )
                        )
                    }
                    is Resource.Failed -> Unit
                }
            }
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
        list: List<DishCompositeItem>,
        querySearch: String
    ): List<DishCompositeItem> {
        return if (querySearch.isEmpty()) {
            list
        } else {
            list.filter {
                it.name.contains(querySearch, ignoreCase = true)
            }
        }
    }

    data class State(
        val dataList: List<DishCompositeItem> = emptyList(),
        val query: String = "",
        val isLoading: Boolean = true
    )

    sealed interface Actions {
        data object OpenAddDishDialog : Actions
        data class OpenEditDishDialog(val item: DishCompositeItem) : Actions
    }

    companion object {
        private const val DELAY_QUERY_SEARCH = 200L
    }

}