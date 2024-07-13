package com.example.databasexmlcourse.features.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.databasexmlcourse.core.BaseViewModel
import com.example.databasexmlcourse.domain.domain_api.UsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val usersUseCase: UsersUseCase
) : BaseViewModel<HomeViewModel.State, HomeViewModel.Actions>(State()) {


    data class State(
        var title: String = ""
    )

    sealed interface Actions {
        data class ShowAlert(val alertData: String) : Actions
    }

    class HomeViewModelFactory @Inject constructor(
        private val usersUseCase: UsersUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(usersUseCase) as T
        }
    }
}