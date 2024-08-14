package com.example.databasexmlcourse.features.feature_profile

import androidx.lifecycle.viewModelScope
import com.example.databasexmlcourse.core.BaseViewModel
import com.example.databasexmlcourse.domain.domain_api.UserTypesUseCase
import com.example.databasexmlcourse.domain.domain_api.UsersUseCase
import com.example.databasexmlcourse.domain.models.User
import com.example.databasexmlcourse.domain.models.UserType
import com.example.databasexmlcourse.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val usersUseCase: UsersUseCase,
    private val userTypesUseCase: UserTypesUseCase
) : BaseViewModel<ProfileViewModel.State, ProfileViewModel.Actions>(State()) {

    fun onExitButtonClick() {
        onAction(Actions.ShowAlert)
    }

    fun setUser(id: String) {
        viewModelScope.launch {
            when (val userResponse = usersUseCase.getUserById(id)) {
                is Resource.Success<*> -> {
                    val user = userResponse.data as User
                    var role = ""
                    when (val roleResponse = userTypesUseCase.getTypeById(user.userType)) {
                        is Resource.Success<*> -> role = (roleResponse.data as UserType).name
                        is Resource.Failed -> Unit
                    }

                    modifyState {
                        copy(name = user.fio, role = role)
                    }
                }
                is Resource.Failed -> Unit
            }
        }
    }

    data class State(
        val name: String = "",
        val role: String = ""
    )

    sealed interface Actions {
        data object ShowAlert : Actions
    }

}