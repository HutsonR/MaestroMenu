package com.example.databasexmlcourse.features.feature_menu.dialogs

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
import com.example.databasexmlcourse.databinding.DialogMenuFragmentBinding
import com.example.databasexmlcourse.domain.models.DishCompositeItem
import com.example.databasexmlcourse.domain.models.DishItem
import com.example.databasexmlcourse.features.common.dialogs.DialogSearcherFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MenuDialogFragment : DialogFragment() {
    private var _binding: DialogMenuFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MenuDialogViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogMenuFragmentBinding.inflate(inflater, container, false)
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
        val item = arguments?.getParcelable<DishCompositeItem>(PARCEL_ITEM)
        item?.let {
            with(binding) {
                nameET.setText(it.name)
                priceET.setText(it.price.toString())
                categoryButton.text = it.category.name
                saveButton.text = getString(R.string.save)
            }
            viewModel.parcelInitialize(it)
        }
    }

    private fun setObservers() {
        viewModel.state.onEach(::handleState).collectOnStart(viewLifecycleOwner)
        viewModel.action.onEach(::handleActions).collectOnStart(viewLifecycleOwner)
    }

    private fun handleState(state: MenuDialogViewModel.State) {
        updateCategoryTextButton(state.currentDish?.category?.name ?: "")
        updateSaveButtonState(state.isSaveButtonEnable)
    }

    private fun handleActions(action: MenuDialogViewModel.Actions) {
        when (action) {
            is MenuDialogViewModel.Actions.OpenCategoryDialog -> DialogSearcherFragment(viewModel.getCategories()) {
                viewModel.updateCategory(it)
            }.show(childFragmentManager, "menu category view fragment")
            is MenuDialogViewModel.Actions.OpenAddCategoryDialog -> MenuDialogAddCategoryFragment().show(childFragmentManager, "menu add category view fragment")
            is MenuDialogViewModel.Actions.GoBack -> dismiss()
            is MenuDialogViewModel.Actions.GoBackWithUpdate -> {
                val bundle = Bundle().apply {
                    putBoolean(MENU_DIALOG_FRAGMENT_RESULT, true)
                }
                activity?.supportFragmentManager?.setFragmentResult(
                    KEY_MENU_DIALOG_FRAGMENT_RESULT, bundle)
                dismiss()
            }
            is MenuDialogViewModel.Actions.ShowFailedText -> binding.addError.visibility = View.VISIBLE
        }
    }

    private fun setListeners() {
        nameListener()
        priceListener()
        categoryButtonListener()
        addCategoryButtonListener()
        deleteButtonListener()
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
        binding.nameET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateName(s.toString())
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun priceListener() {
        binding.priceET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updatePrice(s.toString())
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

    private fun deleteButtonListener() {
        binding.deleteButton.setOnClickListener { viewModel.deleteDish() }
    }

    companion object {
        const val PARCEL_ITEM = "MenuDialogItem"

        const val KEY_MENU_DIALOG_FRAGMENT_RESULT = "KEYMenuFragmentResultDelete"
        const val MENU_DIALOG_FRAGMENT_RESULT = "MenuFragmentResultDelete"
    }
}