package com.example.databasexmlcourse.features.feature_profile

import com.example.databasexmlcourse.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    // UseCase
) : BaseViewModel<ProfileViewModel.State, ProfileViewModel.Actions>(State()) {

    fun onExitButtonClick() {
        onAction(Actions.ShowAlert)
    }

    fun onConfirmExitClick() {
        // TODO - Exit
    }

    data class State(
        val name: String = ""
    )

    sealed interface Actions {
        data object ShowAlert : Actions
    }

}