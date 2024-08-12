package com.example.databasexmlcourse.features.feature_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.databasexmlcourse.R
import com.example.databasexmlcourse.core.BaseFragment
import com.example.databasexmlcourse.core.models.AlertData
import com.example.databasexmlcourse.core.utils.collectOnStart
import com.example.databasexmlcourse.databinding.FragmentProfileBinding
import com.example.databasexmlcourse.features.feature_personal.PersonalViewModel
import com.example.databasexmlcourse.features.feature_personal.adapter.models.PersonalListUiConverter
import com.example.databasexmlcourse.features.feature_personal.dialogs.PersonalDialogFragment
import kotlinx.coroutines.flow.onEach

class ProfileFragment : BaseFragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setObservers()
    }

    private fun initialize() {
        initializeNavBar()
        setListeners()
    }

    private fun initializeNavBar() {
        binding.fragmentToolbar.toolbar.title = getString(R.string.menu_other)
        binding.fragmentToolbar.toolbar.navigationIcon = null
    }

    private fun setListeners() {
        binding.exitButton.setOnClickListener { viewModel.onExitButtonClick() }
    }

    private fun setObservers() {
        viewModel.action.onEach(::handleActions).collectOnStart(viewLifecycleOwner)
    }

    private fun handleActions(action: ProfileViewModel.Actions) {
        when (action) {
            is ProfileViewModel.Actions.ShowAlert -> showAlert(
                AlertData(
                    title = R.string.alert_profile_exit_title,
                    message = R.string.alert_profile_exit_message,
                    positiveButton = R.string.alert_profile_exit,
                    isNegativeButtonNeeded = true,
                    navigate = { viewModel.onConfirmExitClick() }
                )
            )
        }
    }

}