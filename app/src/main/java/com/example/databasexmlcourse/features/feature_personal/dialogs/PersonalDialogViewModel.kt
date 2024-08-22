package com.example.databasexmlcourse.features.feature_personal.dialogs

import androidx.lifecycle.viewModelScope
import com.example.databasexmlcourse.core.BaseViewModel
import com.example.databasexmlcourse.domain.domain_api.UserTypesUseCase
import com.example.databasexmlcourse.domain.domain_api.UsersUseCase
import com.example.databasexmlcourse.domain.models.PersonalItem
import com.example.databasexmlcourse.domain.models.User
import com.example.databasexmlcourse.domain.models.UserType
import com.example.databasexmlcourse.domain.util.Resource
import com.example.databasexmlcourse.features.common.dialogs.adapter.models.DialogSearcherModel
import com.example.databasexmlcourse.features.util.DialogPurposes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PersonalDialogViewModel @Inject constructor(
    private val usersUseCase: UsersUseCase,
    private val userTypesUseCase: UserTypesUseCase
) : BaseViewModel<PersonalDialogViewModel.State, PersonalDialogViewModel.Actions>(State()) {

    init {
        modifyState {
            copy(
                currentUser = PersonalItem(
                    id = "",
                    fio = "",
                    login = "",
                    password = "",
                    type = UserType("", "")
                )
            )
        }
    }

    fun openCategoryDialog() {
        onAction(Actions.OpenCategoryDialog)
    }

    fun openAddCategoryDialog() {
        onAction(Actions.OpenAddCategoryDialog)
    }

    fun deleteUser() {
        viewModelScope.launch {
            usersUseCase.deleteById(getState().currentUser?.id ?: "")
            onAction(Actions.GoBackWithUpdate)
        }
    }

    fun getCategories(): List<DialogSearcherModel> = runBlocking {
        withContext(Dispatchers.IO) {
            val categories = userTypesUseCase.getAll()
            modifyState { copy(userTypes = categories) }
            categories.map { DialogSearcherModel(it.id, it.name) }
        }
    }

    private fun updateCategories(): Deferred<List<UserType>> = viewModelScope.async {
        val categories = userTypesUseCase.getAll()
        modifyState {
            copy(userTypes = categories)
        }
        return@async categories
    }

    fun onActionButtonClick() {
        viewModelScope.launch {
            val currentUser = getState().currentUser
            val result = when (getState().purpose) {
                DialogPurposes.ADD -> handleAddAction(currentUser)
                DialogPurposes.EDIT -> handleEditAction(currentUser)
                else -> Resource.Failed(Exception("Invalid purpose"))
            }

            when (result) {
                is Resource.Success<*> -> onAction(Actions.GoBackWithUpdate)
                is Resource.Failed -> onAction(Actions.ShowFailedText)
            }
        }
    }

    private suspend fun handleAddAction(currentUser: PersonalItem?): Resource {
        return currentUser?.let {
            usersUseCase.insert(it.toUser())
        } ?: Resource.Failed(Exception("User not found"))
    }

    private suspend fun handleEditAction(currentUser: PersonalItem?): Resource {
        return currentUser?.let {
            usersUseCase.update(it.toUser(withId = true))
        } ?: Resource.Failed(Exception("User not found"))
    }

    private fun PersonalItem.toUser(withId: Boolean = false): User {
        return if (withId) {
            User(
                id = this.id,
                fio = this.fio,
                username = this.login,
                password = this.password,
                userType = this.type.id
            )
        } else {
            User(
                fio = this.fio,
                username = this.login,
                password = this.password,
                userType = this.type.id
            )
        }
    }

    fun goBack() {
        onAction(Actions.GoBack)
    }

    fun parcelInitialize(item: PersonalItem) {
        modifyState { copy(purpose = DialogPurposes.EDIT) }
        updateCurrentUser(item)
        updateCategory(item.type.id)
    }

    private fun updateCurrentUser(item: PersonalItem) {
        modifyState { copy(currentUser = item) }
        updateSaveButtonState()
    }

    fun updateFio(text: String) {
        modifyState {
            copy(
                currentUser = currentUser?.copy(fio = text),
            )
        }
        updateSaveButtonState()
    }

    fun updateLogin(text: String) {
        modifyState {
            copy(
                currentUser = currentUser?.copy(login = text),
            )
        }
        updateSaveButtonState()
    }

    fun updatePassword(text: String) {
        modifyState {
            copy(
                currentUser = currentUser?.copy(password = text),
            )
        }
        updateSaveButtonState()
    }

    fun updateCategory(id: String) {
        viewModelScope.launch {
            if (getState().purpose == DialogPurposes.EDIT) {
                updateCategories().await()
            }
            val category = getState().userTypes.find { it.id == id }
            category?.let {
                modifyState {
                    copy(currentUser = currentUser?.copy(type = it))
                }
            }
            updateSaveButtonState()
        }
    }

    private fun updateSaveButtonState() {
        val currentUser = getState().currentUser
        val isEnable = when(getState().purpose) {
            DialogPurposes.ADD -> {
                currentUser != null
                    && currentUser.fio.isNotBlank()
                    && currentUser.login.isNotBlank()
                    && currentUser.password.isNotBlank()
                    && currentUser.type.name.isNotBlank()
            }
            DialogPurposes.EDIT -> {
                currentUser != null
                    && currentUser.fio.isNotBlank()
                    && currentUser.login.isNotBlank()
                    && currentUser.type.name.isNotBlank()
            }
        }

        modifyState { copy(isSaveButtonEnable = isEnable) }
    }

    data class State(
        val currentUser: PersonalItem? = null,
        val userTypes: List<UserType> = emptyList(),
        val isSaveButtonEnable: Boolean = false,
        val purpose: DialogPurposes = DialogPurposes.ADD
    )

    sealed interface Actions {
        data object OpenCategoryDialog : Actions
        data object OpenAddCategoryDialog : Actions
        data object ShowFailedText : Actions
        data object GoBackWithUpdate : Actions
        data object GoBack : Actions
    }

}