package com.example.databasexmlcourse.features.feature_menu.dialogs

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databasexmlcourse.core.composite.adapter.CompositeAdapter
import com.example.databasexmlcourse.core.utils.collectOnStart
import com.example.databasexmlcourse.databinding.DialogFragmentCategoryMenuBinding
import com.example.databasexmlcourse.features.feature_menu.adapter.MenuCategoryDelegate
import kotlinx.coroutines.flow.onEach
import kotlin.properties.Delegates


class MenuDialogRecyclerFragment : DialogFragment() {
    private var _binding: DialogFragmentCategoryMenuBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MenuDialogRecyclerViewModel by viewModels()
    private var adapter: CompositeAdapter by Delegates.notNull()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogFragmentCategoryMenuBinding.inflate(inflater, container, false)
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
        binding.closeButton.setOnClickListener {
            viewModel.goBack()
        }
        setRecycler()
        setListeners()
    }

    private fun setRecycler() {
        adapter = CompositeAdapter
            .Builder()
            .add(MenuCategoryDelegate(viewModel::onItemClick))
            .build()

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
    }

    private fun setObservers() {
        viewModel.state.onEach(::handleState).collectOnStart(viewLifecycleOwner)
        viewModel.action.onEach(::handleActions).collectOnStart(viewLifecycleOwner)
    }

    private fun handleState(state: MenuDialogRecyclerViewModel.State) {
        adapter.submitList(state.dataList)
    }

    private fun handleActions(action: MenuDialogRecyclerViewModel.Actions) {
        when (action) {
            is MenuDialogRecyclerViewModel.Actions.GoBackWithItem -> {
                val bundle = Bundle().apply {
                    putString(KEY_CATEGORY_ITEM, action.text)
                }
                activity?.supportFragmentManager?.setFragmentResult(REQ_KEY_CATEGORY_ITEM, bundle)
                viewModel.goBack()
            }
            is MenuDialogRecyclerViewModel.Actions.GoBack -> dismiss()
        }
    }

    private fun setListeners() {
        searchListener()
    }

//    Listeners
    private fun searchListener() {
        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                viewModel.onChangeQuerySearch(s.toString())
            }
        })
    }

    companion object {
        const val REQ_KEY_CATEGORY_ITEM = "REQ_KEY_DIALOG_CATEGORY_ITEM"
        const val KEY_CATEGORY_ITEM = "KEY_DIALOG_CATEGORY_ITEM"
    }
}