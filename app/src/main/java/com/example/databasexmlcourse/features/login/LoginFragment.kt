package com.example.databasexmlcourse.features.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.databasexmlcourse.R
import com.example.databasexmlcourse.core.BaseFragment
import com.example.databasexmlcourse.core.SecurePreferences
import com.example.databasexmlcourse.core.models.AlertData
import com.example.databasexmlcourse.core.utils.collectOnStart
import com.example.databasexmlcourse.databinding.FragmentLoginBinding
import com.example.databasexmlcourse.databinding.FragmentProfileBinding
import com.example.databasexmlcourse.features.feature_personal.PersonalViewModel
import com.example.databasexmlcourse.features.feature_personal.adapter.models.PersonalListUiConverter
import com.example.databasexmlcourse.features.feature_personal.dialogs.PersonalDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setObservers()
    }

    private fun initialize() {
        checkAuth()
        setListeners()
    }

    private fun checkAuth() {
        if (SecurePreferences(requireContext()).contains(AUTH_USER_ID)) {
            viewModel.checkAuth(SecurePreferences(requireContext()).get(AUTH_USER_ID, ""))
        }
    }

    private fun setListeners() {
        binding.loginButton.setOnClickListener { viewModel.onLoginButtonClick() }
        usernameListener()
        passwordListener()
    }

    private fun setObservers() {
        viewModel.action.onEach(::handleActions).collectOnStart(viewLifecycleOwner)
    }

    private fun handleActions(action: LoginViewModel.Actions) {
        when (action) {
            is LoginViewModel.Actions.ShowFailedAlert -> showAlert(
                AlertData(
                    title = R.string.error_title,
                    message = R.string.login_error_alert
                )
            )
            is LoginViewModel.Actions.LoginSuccess -> {
                SecurePreferences(requireContext()).put(AUTH_USER_ID, action.userId)
                navigateTo(R.id.action_to_homeFragment)
            }

            is LoginViewModel.Actions.GoToHome -> navigateTo(R.id.action_to_homeFragment)
        }
    }

    private fun usernameListener() {
        binding.username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                viewModel.onChangeUsername(s.toString())
            }
        })
    }

    private fun passwordListener() {
        binding.password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                viewModel.onChangePassword(s.toString())
            }
        })
    }

    companion object {
        private const val AUTH_USER_ID = "auth_user_id"
    }

}