package com.example.databasexmlcourse.features.feature_menu.dialogs

import androidx.lifecycle.viewModelScope
import com.example.databasexmlcourse.core.BaseViewModel
import com.example.databasexmlcourse.domain.domain_api.DishCategoriesUseCase
import com.example.databasexmlcourse.domain.domain_api.DishesUseCase
import com.example.databasexmlcourse.domain.models.DishCategory
import com.example.databasexmlcourse.domain.models.DishCompositeItem
import com.example.databasexmlcourse.domain.models.DishItem
import com.example.databasexmlcourse.domain.models.PersonalItem
import com.example.databasexmlcourse.domain.models.User
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
class MenuDialogViewModel @Inject constructor(
    private val dishesUseCase: DishesUseCase,
    private val dishCategoriesUseCase: DishCategoriesUseCase
) : BaseViewModel<MenuDialogViewModel.State, MenuDialogViewModel.Actions>(State()) {

    init {
        modifyState {
            copy(
                currentDish = DishCompositeItem(
                    id = "",
                    name = "",
                    price = 0,
                    category = DishCategory("" , "")
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

    fun deleteDish() {
        viewModelScope.launch {
            dishesUseCase.deleteById(getState().currentDish?.id ?: "")
            onAction(Actions.GoBackWithUpdate)
        }
    }

    fun getCategories(): List<DialogSearcherModel> = runBlocking {
        withContext(Dispatchers.IO) {
            val categories = dishCategoriesUseCase.getAll()
            modifyState { copy(dishCategories = categories) }
            categories.map { DialogSearcherModel(it.id, it.name) }
        }
    }

    fun onActionButtonClick() = viewModelScope.launch {
        val currentDish = getState().currentDish
        val result = when (getState().purpose) {
            DialogPurposes.ADD -> handleAddAction(currentDish)
            DialogPurposes.EDIT -> handleEditAction(currentDish)
            else -> Resource.Failed(Exception("Invalid purpose"))
        }

        when (result) {
            is Resource.Success<*> -> onAction(Actions.GoBackWithUpdate)
            is Resource.Failed -> onAction(Actions.ShowFailedText)
        }
    }

    private suspend fun handleAddAction(currentUser: DishCompositeItem?): Resource {
        return currentUser?.let {
            dishesUseCase.insert(it.toDishItem())
        } ?: Resource.Failed(Exception("User not found"))
    }

    private suspend fun handleEditAction(currentUser: DishCompositeItem?): Resource {
        return currentUser?.let {
            dishesUseCase.update(it.toDishItem(withId = true))
        } ?: Resource.Failed(Exception("User not found"))
    }

    private fun DishCompositeItem.toDishItem(withId: Boolean = false): DishItem {
        return if (withId) {
            DishItem(
                id = this.id,
                name = this.name,
                price = this.price,
                dishCategoryId = this.category.id,
                count = this.count
            )
        } else {
            DishItem(
                name = this.name,
                price = this.price,
                dishCategoryId = this.category.id,
                count = this.count
            )
        }
    }

    fun goBack() {
        onAction(Actions.GoBack)
    }

    fun parcelInitialize(item: DishCompositeItem) {
        modifyState { copy(purpose = DialogPurposes.EDIT) }
        updateCurrentDish(item)
        updateCategory(item.category.id)
    }

    private fun updateCurrentDish(item: DishCompositeItem) {
        modifyState { copy(currentDish = item) }
        updateSaveButtonState()
    }

    fun updateName(text: String) {
        modifyState {
            copy(
                currentDish = currentDish?.copy(name = text)
            )
        }
        updateSaveButtonState()
    }

    fun updatePrice(price: String) {
        modifyState {
            copy(
                currentDish = currentDish?.copy(price = price.toIntOrNull() ?: 0)
            )
        }
        updateSaveButtonState()
    }

    private fun updateCategories(): Deferred<List<DishCategory>> = viewModelScope.async {
        val categories = dishCategoriesUseCase.getAll()
        modifyState {
            copy(dishCategories = categories)
        }
        return@async categories
    }

    fun updateCategory(id: String) = viewModelScope.launch {
        if (getState().purpose == DialogPurposes.EDIT) {
            updateCategories().await()
        }
        val category = getState().dishCategories.find { it.id == id }
        category?.let {
            modifyState {
                copy(currentDish = currentDish?.copy(category = it))
            }
        }
        updateSaveButtonState()
    }

    private fun updateSaveButtonState() {
        val currentDish = getState().currentDish
        val isEnable = currentDish != null
                && currentDish.name.isNotBlank()
                && currentDish.price > 0
                && currentDish.category.name.isNotBlank()

        modifyState { copy(isSaveButtonEnable = isEnable) }
    }

    data class State(
        val currentDish: DishCompositeItem? = null,
        val dishCategories: List<DishCategory> = emptyList(),
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