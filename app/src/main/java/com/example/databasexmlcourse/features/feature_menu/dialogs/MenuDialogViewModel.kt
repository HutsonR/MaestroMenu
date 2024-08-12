package com.example.databasexmlcourse.features.feature_menu.dialogs

import com.example.databasexmlcourse.core.BaseViewModel
import com.example.databasexmlcourse.domain.models.DishItem
import com.example.databasexmlcourse.features.common.dialogs.adapter.models.DialogSearcherModel
import com.example.databasexmlcourse.features.util.DialogPurposes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuDialogViewModel @Inject constructor(
    // useCase
) : BaseViewModel<MenuDialogViewModel.State, MenuDialogViewModel.Actions>(State()) {

    val categoryList = listOf(
        DialogSearcherModel("1", "Закуски"),
        DialogSearcherModel("2", "Горячие блюда"),
        DialogSearcherModel("3", "Десерты")
    )

    fun openCategoryDialog() {
        onAction(Actions.OpenCategoryDialog)
    }

    fun openAddCategoryDialog() {
        onAction(Actions.OpenAddCategoryDialog)
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

    fun parcelInitialize(item: DishItem) {
        modifyState { copy(purpose = DialogPurposes.EDIT) }
        updateText(item.name)
        updatePrice(item.price.toString())
        updateCategory(item.dishCategoryId)
    }

    fun updateText(text: String) {
        modifyState { copy(text = text) }
        updateSaveButtonState()
    }

    fun updatePrice(price: String) {
        modifyState { copy(price = price) }
        updateSaveButtonState()
    }

    fun updateCategory(text: String) {
        modifyState { copy(category = text) }
        updateSaveButtonState()
    }

    private fun updateSaveButtonState() {
        val isEnable = getState().text.isNotBlank() && getState().price.isNotBlank() && getState().category.isNotBlank()
        modifyState { copy(isSaveButtonEnable = isEnable) }
    }

    data class State(
        val text: String = "",
        val price: String = "",
        val category: String = "",
        val isSaveButtonEnable: Boolean = false,
        val purpose: DialogPurposes = DialogPurposes.ADD
    )

    sealed interface Actions {
        data object OpenCategoryDialog : Actions
        data object OpenAddCategoryDialog : Actions
        data object GoBack : Actions
    }

}