package com.example.databasexmlcourse.features.feature_menu.dialogs

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.databasexmlcourse.core.utils.collectOnStart
import com.example.databasexmlcourse.databinding.DialogFragmentAddCategoryMenuBinding
import com.example.databasexmlcourse.features.feature_personal.dialogs.PersonalDialogAddCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MenuDialogAddCategoryFragment : DialogFragment() {
    private var _binding: DialogFragmentAddCategoryMenuBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MenuDialogAddCategoryViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogFragmentAddCategoryMenuBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialize()
        setObservers()
    }

    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog!!.window?.setLayout(width, height)
    }

    private fun initialize() {
        binding.saveButton.setOnClickListener {
            viewModel.onActionButtonClick()
        }
        binding.closeButton.setOnClickListener {
            viewModel.goBack()
        }

        setListeners()
    }

    private fun setObservers() {
        viewModel.state.onEach(::handleState).collectOnStart(viewLifecycleOwner)
        viewModel.action.onEach(::handleActions).collectOnStart(viewLifecycleOwner)
    }

    private fun handleState(state: MenuDialogAddCategoryViewModel.State) {
        updateSaveButtonState(state.isSaveButtonEnable)
    }

    private fun handleActions(action: MenuDialogAddCategoryViewModel.Actions) {
        when (action) {
            is MenuDialogAddCategoryViewModel.Actions.GoBack -> dismiss()
            is MenuDialogAddCategoryViewModel.Actions.ShowFailedText -> binding.addCategoryError.visibility = View.VISIBLE
        }
    }

    private fun setListeners() {
        nameListener()
    }

    private fun updateSaveButtonState(isEnabled: Boolean) {
        binding.saveButton.isEnabled = isEnabled
        binding.saveButton.alpha = if (isEnabled) 1.0f else 0.5f
    }

//    Listeners
    private fun nameListener() {
        binding.nameET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                viewModel.updateText(s.toString())
            }
        })
    }

}