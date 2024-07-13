package com.example.databasexmlcourse.features.feature_menu.dialogs

import androidx.lifecycle.viewModelScope
import com.example.databasexmlcourse.core.BaseViewModel
import com.example.databasexmlcourse.features.feature_menu.adapter.models.MenuCategoryListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuDialogRecyclerViewModel @Inject constructor(
    // useCase
) : BaseViewModel<MenuDialogRecyclerViewModel.State, MenuDialogRecyclerViewModel.Actions>(State()) {

    private var jobChangeQuerySearch: Job? = null
    private var list: List<MenuCategoryListItem> = emptyList()

    init {
        viewModelScope.launch {
            list = listOf(
                MenuCategoryListItem("1", "Закуски"),
                MenuCategoryListItem("2", "Напитки")
            )
            modifyState { copy(dataList = list) }
        }
    }

    fun onItemClick(text: String) {
        onAction(Actions.GoBackWithItem(text))
    }

    fun goBack() {
        onAction(Actions.GoBack)
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
        list: List<MenuCategoryListItem>,
        querySearch: String
    ): List<MenuCategoryListItem> {
        return if (querySearch.isEmpty()) {
            list
        } else {
            list.filter {
                it.text.contains(querySearch, ignoreCase = true)
            }
        }
    }

    data class State(
        val dataList: List<MenuCategoryListItem> = emptyList(),
        val query: String = "",
    )

    sealed interface Actions {
        data class GoBackWithItem(val text: String) : Actions
        data object GoBack : Actions
    }

    companion object {
        private const val DELAY_QUERY_SEARCH = 200L
    }

}