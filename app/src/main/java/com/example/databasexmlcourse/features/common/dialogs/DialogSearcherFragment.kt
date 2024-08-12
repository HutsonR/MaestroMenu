package com.example.databasexmlcourse.features.common.dialogs

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
import com.example.databasexmlcourse.databinding.DialogFragmentCategoryBinding
import com.example.databasexmlcourse.features.common.dialogs.adapter.DialogSearcherDelegate
import com.example.databasexmlcourse.features.common.dialogs.adapter.models.DialogSearcherListUiConverter
import com.example.databasexmlcourse.features.common.dialogs.adapter.models.DialogSearcherModel
import com.example.databasexmlcourse.features.feature_menu.adapter.MenuCategoryDelegate
import kotlinx.coroutines.flow.onEach
import kotlin.properties.Delegates


class DialogSearcherFragment(
    private val list: List<DialogSearcherModel>,
    private val onItemClick: (text: String) -> Unit
) : DialogFragment() {
    private var _binding: DialogFragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DialogSearcherViewModel by viewModels()
    private var adapter: CompositeAdapter by Delegates.notNull()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogFragmentCategoryBinding.inflate(inflater, container, false)
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
        viewModel.init(list)
        binding.closeButton.setOnClickListener {
            viewModel.goBack()
        }
        setRecycler()
        setListeners()
    }

    private fun setRecycler() {
        adapter = CompositeAdapter
            .Builder()
            .add(DialogSearcherDelegate(viewModel::onItemClick))
            .build()

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
    }

    private fun setObservers() {
        viewModel.state.onEach(::handleState).collectOnStart(viewLifecycleOwner)
        viewModel.action.onEach(::handleActions).collectOnStart(viewLifecycleOwner)
    }

    private fun handleState(state: DialogSearcherViewModel.State) {
        val list = DialogSearcherListUiConverter().convertToListItem(state.dataList)
        adapter.submitList(list)
    }

    private fun handleActions(action: DialogSearcherViewModel.Actions) {
        when (action) {
            is DialogSearcherViewModel.Actions.GoBackWithItem -> {
                onItemClick(action.text)
                viewModel.goBack()
            }
            is DialogSearcherViewModel.Actions.GoBack -> dismiss()
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
}