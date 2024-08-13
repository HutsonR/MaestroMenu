package com.example.databasexmlcourse.features.feature_personal

import androidx.lifecycle.viewModelScope
import com.example.databasexmlcourse.core.BaseViewModel
import com.example.databasexmlcourse.domain.domain_api.UsersUseCase
import com.example.databasexmlcourse.domain.models.PersonalItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel @Inject constructor(
    private val usersUseCase: UsersUseCase
) : BaseViewModel<PersonalViewModel.State, PersonalViewModel.Actions>(State()) {

    private var jobChangeQuerySearch: Job? = null
    private var list: List<PersonalItem> = emptyList()

    init {
        viewModelScope.launch {
            modifyState { copy(isLoading = true) }

//            list = listOf(
//                PersonalItem(id = "1", name = "Роман Тузов", type = "Администратор"),
//                PersonalItem(id = "2", name = "Иван Иванов", type = "Менеджер"),
//                PersonalItem(id = "3", name = "Петя Петрович", type = "Официант")
//            )
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
            onAction(Actions.OpenEditDialog(it))
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
        list: List<PersonalItem>,
        querySearch: String
    ): List<PersonalItem> {
        return if (querySearch.isEmpty()) {
            list
        } else {
            list.filter {
                it.fio.contains(querySearch, ignoreCase = true)
            }
        }
    }

    fun openAddDialog() {
        onAction(Actions.OpenAddDialog)
    }

    data class State(
        val dataList: List<PersonalItem> = emptyList(),
        val query: String = "",
        val isLoading: Boolean = true
    )

    sealed interface Actions {
        data object OpenAddDialog : Actions
        data class OpenEditDialog(val item: PersonalItem) : Actions
    }

    companion object {
        private const val DELAY_QUERY_SEARCH = 200L
    }
}