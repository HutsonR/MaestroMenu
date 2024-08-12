package com.example.databasexmlcourse.features.common.dialogs

import androidx.lifecycle.viewModelScope
import com.example.databasexmlcourse.core.BaseViewModel
import com.example.databasexmlcourse.features.common.dialogs.adapter.models.DialogSearcherModel
import com.example.databasexmlcourse.features.feature_menu.adapter.models.MenuCategoryListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DialogSearcherViewModel @Inject constructor(
    // useCase
) : BaseViewModel<DialogSearcherViewModel.State, DialogSearcherViewModel.Actions>(State()) {

    private var jobChangeQuerySearch: Job? = null
    private var list: List<DialogSearcherModel> = emptyList()

    fun init(list: List<DialogSearcherModel>) {
        this.list = list
        viewModelScope.launch {
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
        list: List<DialogSearcherModel>,
        querySearch: String
    ): List<DialogSearcherModel> {
        return if (querySearch.isEmpty()) {
            list
        } else {
            list.filter {
                it.text.contains(querySearch, ignoreCase = true)
            }
        }
    }

    data class State(
        val dataList: List<DialogSearcherModel> = emptyList(),
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