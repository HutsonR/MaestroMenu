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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PersonalDialogViewModel @Inject constructor(
    private val usersUseCase: UsersUseCase,
    private val userTypesUseCase: UserTypesUseCase
) : BaseViewModel<PersonalDialogViewModel.State, PersonalDialogViewModel.Actions>(State()) {

    fun openCategoryDialog() {
        onAction(Actions.OpenCategoryDialog)
    }

    fun openAddCategoryDialog() {
        onAction(Actions.OpenAddCategoryDialog)
    }

    fun getCategories(): List<DialogSearcherModel> = runBlocking {
        withContext(Dispatchers.IO) {
            val categories = userTypesUseCase.getAll()
            modifyState {
                copy(userTypes = categories)
            }
            categories.map { DialogSearcherModel(it.id, it.name) }
        }
    }

    fun onActionButtonClick() {
        viewModelScope.launch {
            when(getState().purpose) {
                DialogPurposes.ADD -> {
                    val result = usersUseCase.insert(
                        User(
                            fio = getState().fio,
                            username = getState().login,
                            password = getState().password,
                            userType = getState().category?.id ?: ""
                        )
                    )

                    when (result) {
                        is Resource.Success<*> -> {
                            onAction(Actions.GoBack)
                        }
                        is Resource.Failed -> {
                            onAction(Actions.ShowFailedText)
                        }
                    }
                }
                DialogPurposes.EDIT -> {
                    // TODO Изменить пользователя
                }
            }
        }
    }

    fun goBack() {
        onAction(Actions.GoBack)
    }

    fun parcelInitialize(item: PersonalItem) {
        modifyState { copy(purpose = DialogPurposes.EDIT) }
        updateFio(item.fio)
        updateCategory(item.type)
    }

    fun updateFio(text: String) {
        modifyState { copy(fio = text) }
        updateSaveButtonState()
    }

    fun updateLogin(text: String) {
        modifyState { copy(login = text) }
        updateSaveButtonState()
    }

    fun updatePassword(text: String) {
        modifyState { copy(password = text) }
        updateSaveButtonState()
    }

    fun updateCategory(text: String) {
        val category = getState().userTypes.find { it.id == text }
        category?.let {
            modifyState { copy(category = it) }
        }
        updateSaveButtonState()
    }

    private fun updateSaveButtonState() {
        val isEnable = getState().fio.isNotBlank()
                && getState().category?.name?.isNotBlank() ?: false
                && getState().login.isNotBlank()
                && getState().password.isNotBlank()

        modifyState { copy(isSaveButtonEnable = isEnable) }
    }

    data class State(
        val fio: String = "",
        val login: String = "",
        val password: String = "",
        val category: UserType? = null,
        val userTypes: List<UserType> = emptyList(),
        val isSaveButtonEnable: Boolean = false,
        val purpose: DialogPurposes = DialogPurposes.ADD
    )

    sealed interface Actions {
        data object OpenCategoryDialog : Actions
        data object OpenAddCategoryDialog : Actions
        data object ShowFailedText : Actions
        data object GoBack : Actions
    }

}