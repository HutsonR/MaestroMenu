package com.example.databasexmlcourse.features.feature_personal.dialogs

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.databasexmlcourse.R
import com.example.databasexmlcourse.core.utils.collectOnStart
import com.example.databasexmlcourse.databinding.DialogPersonalFragmentBinding
import com.example.databasexmlcourse.domain.models.PersonalItem
import com.example.databasexmlcourse.features.common.dialogs.DialogSearcherFragment
import com.example.databasexmlcourse.features.feature_menu.dialogs.MenuDialogAddCategoryFragment
import com.example.databasexmlcourse.features.feature_menu.dialogs.MenuDialogViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PersonalDialogFragment : DialogFragment() {
    private var _binding: DialogPersonalFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PersonalDialogViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogPersonalFragmentBinding.inflate(inflater, container, false)
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
        parseArguments()
        binding.saveButton.setOnClickListener {
            viewModel.onActionButtonClick()
        }
        binding.closeButton.setOnClickListener {
            viewModel.goBack()
        }

        setListeners()
    }

    private fun parseArguments() {
        val item = arguments?.getParcelable<PersonalItem>(PARCEL_ITEM)
        item?.let {
            with(binding) {
                fioET.setText(it.fio)
                loginET.setText(it.login)
                passwordET.setText(it.password)
                categoryButton.text = it.type
                saveButton.text = getString(R.string.save)
            }
            viewModel.parcelInitialize(it)
        }
    }

    private fun setObservers() {
        viewModel.state.onEach(::handleState).collectOnStart(viewLifecycleOwner)
        viewModel.action.onEach(::handleActions).collectOnStart(viewLifecycleOwner)
    }

    private fun handleState(state: PersonalDialogViewModel.State) {
        updateCategoryTextButton(state.category?.name ?: "")
        updateSaveButtonState(state.isSaveButtonEnable)
    }

    private fun handleActions(action: PersonalDialogViewModel.Actions) {
        when (action) {
            is PersonalDialogViewModel.Actions.OpenCategoryDialog -> DialogSearcherFragment(viewModel.getCategories()){
                viewModel.updateCategory(it)
            }.show(childFragmentManager, "personal category view fragment")
            is PersonalDialogViewModel.Actions.OpenAddCategoryDialog -> PersonalDialogAddCategoryFragment().show(childFragmentManager, "personal add category view fragment")
            is PersonalDialogViewModel.Actions.GoBack -> dismiss()
            is PersonalDialogViewModel.Actions.ShowFailedText -> binding.addError.visibility = View.VISIBLE
        }
    }

    private fun setListeners() {
        nameListener()
        loginListener()
        passwordListener()
        categoryButtonListener()
        addCategoryButtonListener()
    }

    private fun updateCategoryTextButton(text: String) {
        if (text.isNotBlank()) {
            binding.categoryButton.text = text
        }
    }

    private fun updateSaveButtonState(isEnabled: Boolean) {
        binding.saveButton.isEnabled = isEnabled
        binding.saveButton.alpha = if (isEnabled) 1.0f else 0.5f
    }

//    Listeners
    private fun nameListener() {
        binding.fioET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateFio(s.toString())
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun loginListener() {
        binding.loginET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateLogin(s.toString())
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun passwordListener() {
        binding.passwordET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updatePassword(s.toString())
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun categoryButtonListener() {
        binding.categoryButton.setOnClickListener { viewModel.openCategoryDialog() }
    }

    private fun addCategoryButtonListener() {
        binding.addCategoryButton.setOnClickListener { viewModel.openAddCategoryDialog() }
    }

    companion object {
        const val PARCEL_ITEM = "PersonalDialogItem"
    }
}