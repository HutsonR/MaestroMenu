package com.example.databasexmlcourse.features.feature_personal

import androidx.lifecycle.viewModelScope
import com.example.databasexmlcourse.core.BaseViewModel
import com.example.databasexmlcourse.domain.domain_api.UserTypesUseCase
import com.example.databasexmlcourse.domain.domain_api.UsersUseCase
import com.example.databasexmlcourse.domain.models.PersonalItem
import com.example.databasexmlcourse.domain.models.UserType
import com.example.databasexmlcourse.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel @Inject constructor(
    private val usersUseCase: UsersUseCase,
    private val userTypesUseCase: UserTypesUseCase
) : BaseViewModel<PersonalViewModel.State, PersonalViewModel.Actions>(State()) {

    private var jobChangeQuerySearch: Job? = null
    private var list: MutableList<PersonalItem> = mutableListOf()

    init {
        updateList()
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

    fun updateList() {
        viewModelScope.launch {
            modifyState {
                copy(
                    dataList = emptyList(),
                    isLoading = true
                )
            }
            list.clear()
            val users = usersUseCase.getAll()
            users.forEach { user ->
                when (val roleResponse = userTypesUseCase.getTypeById(user.userType)) {
                    is Resource.Success<*> -> {
                        val role = (roleResponse.data as UserType)
                        list.add(
                            PersonalItem(
                                id = user.id,
                                fio = user.fio,
                                login = user.username,
                                password = user.password,
                                type = role
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