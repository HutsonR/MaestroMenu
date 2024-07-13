package com.example.databasexmlcourse.features.feature_orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.databasexmlcourse.core.BaseViewModel
import com.example.databasexmlcourse.domain.domain_api.UsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val usersUseCase: UsersUseCase
) : BaseViewModel<OrdersViewModel.State, OrdersViewModel.Actions>(State()) {


    data class State(
        var title: String = ""
    )

    sealed interface Actions {
        data class ShowAlert(val alertData: String) : Actions
    }

    class OrdersViewModelFactory @Inject constructor(
        private val usersUseCase: UsersUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return OrdersViewModel(usersUseCase) as T
        }
    }
}