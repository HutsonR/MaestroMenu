package com.example.databasexmlcourse.features.feature_personal.dialogs

import androidx.lifecycle.viewModelScope
import com.example.databasexmlcourse.core.BaseViewModel
import com.example.databasexmlcourse.domain.domain_api.UserTypesUseCase
import com.example.databasexmlcourse.domain.models.User
import com.example.databasexmlcourse.domain.models.UserType
import com.example.databasexmlcourse.domain.util.Resource
import com.example.databasexmlcourse.features.login.LoginViewModel.Actions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalDialogAddCategoryViewModel @Inject constructor(
    private val userTypesUseCase: UserTypesUseCase
) : BaseViewModel<PersonalDialogAddCategoryViewModel.State, PersonalDialogAddCategoryViewModel.Actions>(State()) {

    fun onActionButtonClick() = viewModelScope.launch {
        val result = userTypesUseCase.insert(
            UserType(name = getState().name)
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

    fun goBack() {
        onAction(Actions.GoBack)
    }

    fun updateText(text: String) {
        modifyState { copy(name = text) }
        updateSaveButtonState()
    }

    private fun updateSaveButtonState() {
        val isEnable = getState().name.isNotBlank()
        modifyState { copy(isSaveButtonEnable = isEnable) }
    }

    data class State(
        val name: String = "",
        val isSaveButtonEnable: Boolean = false
    )

    sealed interface Actions {
        data object GoBack : Actions
        data object ShowFailedText : Actions
    }

}