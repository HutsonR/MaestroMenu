package com.example.databasexmlcourse.features.feature_menu.dialogs

import androidx.lifecycle.viewModelScope
import com.example.databasexmlcourse.core.BaseViewModel
import com.example.databasexmlcourse.features.feature_menu.adapter.MenuCategoryListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuDialogAddCategoryViewModel @Inject constructor(
    // useCase
) : BaseViewModel<MenuDialogAddCategoryViewModel.State, MenuDialogAddCategoryViewModel.Actions>(State()) {

    fun onActionButtonClick() {
        // TODO проверить, что добавилась категория и закрыть окно
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
    }

}