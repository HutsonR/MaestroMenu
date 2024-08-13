package com.example.databasexmlcourse.features.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.databasexmlcourse.core.BaseViewModel
import com.example.databasexmlcourse.domain.domain_api.UsersUseCase
import com.example.databasexmlcourse.domain.models.User
import com.example.databasexmlcourse.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usersUseCase: UsersUseCase
) : BaseViewModel<LoginViewModel.State, LoginViewModel.Actions>(State()) {

    fun onLoginButtonClick() {
        viewModelScope.launch {
            val result = usersUseCase.checkUser(getState().username, getState().password)
            Log.d("debugTag", "result: $result")
            when (result) {
                is Resource.Success<*> -> {
                    onAction(Actions.LoginSuccess(result.data.toString()))
                }
                is Resource.Failed -> {
                    onAction(Actions.ShowFailedAlert)
                }
            }
        }
    }

    fun checkAuth(userId: String) {
        viewModelScope.launch {
            val result = usersUseCase.checkUserById(userId)
            Log.d("debugTag", "checkAuth result: $result")
            if (result) {
                onAction(Actions.GoToHome)
            }
        }
    }

    fun onChangeUsername(username: String) {
        viewModelScope.launch {
            modifyState {
                copy(username = username)
            }
        }
    }

    fun onChangePassword(password: String) {
        viewModelScope.launch {
            modifyState {
                copy(password = password)
            }
        }
    }

    data class State(
        val username: String = "",
        val password: String = ""
    )

    sealed interface Actions {
        data object ShowFailedAlert : Actions
        data object GoToHome : Actions
        data class LoginSuccess(val userId: String) : Actions
    }

}