package com.example.databasexmlcourse.features.feature_personal.dialogs

import com.example.databasexmlcourse.core.BaseViewModel
import com.example.databasexmlcourse.domain.models.DishItem
import com.example.databasexmlcourse.domain.models.PersonalItem
import com.example.databasexmlcourse.features.util.DialogPurposes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonalDialogViewModel @Inject constructor(
    // useCase
) : BaseViewModel<PersonalDialogViewModel.State, PersonalDialogViewModel.Actions>(State()) {

    fun openCategoryDialog() {
        onAction(Actions.OpenCategoryDialog)
    }

    fun onActionButtonClick() {
        when(getState().purpose) {
            DialogPurposes.ADD -> {
                // TODO Добавить блюдо в меню
            }
            DialogPurposes.EDIT -> {
                // TODO Изменить блюдо в меню
            }
        }
    }

    fun goBack() {
        onAction(Actions.GoBack)
    }

    fun parcelInitialize(item: PersonalItem) {
        modifyState { copy(purpose = DialogPurposes.EDIT) }
        updateText(item.name)
        updateCategory(item.type)
    }

    fun updateText(text: String) {
        modifyState { copy(name = text) }
        updateSaveButtonState()
    }

    fun updateCategory(text: String) {
        modifyState { copy(category = text) }
        updateSaveButtonState()
    }

    private fun updateSaveButtonState() {
        val isEnable = getState().name.isNotBlank() && getState().category.isNotBlank()
        modifyState { copy(isSaveButtonEnable = isEnable) }
    }

    data class State(
        val name: String = "",
        val category: String = "",
        val isSaveButtonEnable: Boolean = false,
        val purpose: DialogPurposes = DialogPurposes.ADD
    )

    sealed interface Actions {
        data object OpenCategoryDialog : Actions
        data object GoBack : Actions
    }

}