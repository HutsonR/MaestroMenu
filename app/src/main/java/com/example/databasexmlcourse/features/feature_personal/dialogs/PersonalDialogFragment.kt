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
import com.example.databasexmlcourse.databinding.DialogFragmentWithCategoryBinding
import com.example.databasexmlcourse.domain.models.DishItem
import com.example.databasexmlcourse.domain.models.PersonalItem
import kotlinx.coroutines.flow.onEach


class PersonalDialogFragment : DialogFragment() {
    private var _binding: DialogFragmentWithCategoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PersonalDialogViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogFragmentWithCategoryBinding.inflate(inflater, container, false)
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
        binding.nameLayout.hint = getString(R.string.personal_fio)
        binding.priceET.visibility = View.GONE
        binding.categoryButton.text = getString(R.string.personal_type)
        binding.addCategoryButton.visibility = View.GONE
        binding.saveButton.text = getString(R.string.personal_add_button)

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
                nameET.setText(it.name)
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
        updateCategoryTextButton(state.category)
        updateSaveButtonState(state.isSaveButtonEnable)
    }

    private fun handleActions(action: PersonalDialogViewModel.Actions) {
        when (action) {
            is PersonalDialogViewModel.Actions.OpenCategoryDialog -> PersonalDialogRecyclerFragment().show(childFragmentManager, "personal category view fragment")
            is PersonalDialogViewModel.Actions.GoBack -> dismiss()
        }
    }

    private fun setListeners() {
        setFragmentListener()
        nameListener()
        categoryButtonListener()
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
    private fun setFragmentListener() {
        // Из MenuDialogRecyclerFragment
        activity?.supportFragmentManager?.setFragmentResultListener(
            PersonalDialogRecyclerFragment.REQ_KEY_CATEGORY_ITEM,
            this
        ) { _, bundle ->
            val requestValue: String? = bundle.getString(PersonalDialogRecyclerFragment.KEY_CATEGORY_ITEM)
            requestValue?.let {
                viewModel.updateCategory(requestValue)
            }
        }
    }

    private fun nameListener() {
        binding.nameET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateText(s.toString())
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun categoryButtonListener() {
        binding.categoryButton.setOnClickListener { viewModel.openCategoryDialog() }
    }

    companion object {
        const val PARCEL_ITEM = "PersonalDialogItem"
    }
}