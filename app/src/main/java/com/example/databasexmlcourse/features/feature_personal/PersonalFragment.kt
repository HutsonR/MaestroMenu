package com.example.databasexmlcourse.features.feature_personal

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databasexmlcourse.R
import com.example.databasexmlcourse.core.BaseFragment
import com.example.databasexmlcourse.core.composite.adapter.CompositeAdapter
import com.example.databasexmlcourse.core.utils.collectOnStart
import com.example.databasexmlcourse.databinding.FragmentPersonalBinding
import com.example.databasexmlcourse.features.feature_personal.adapter.PersonalDelegate
import com.example.databasexmlcourse.features.feature_personal.adapter.PersonalLoadingDelegate
import com.example.databasexmlcourse.features.feature_personal.adapter.models.PersonalListUiConverter
import com.example.databasexmlcourse.features.feature_personal.dialogs.PersonalDialogFragment
import kotlinx.coroutines.flow.onEach
import kotlin.properties.Delegates

class PersonalFragment : BaseFragment() {
    private var _binding: FragmentPersonalBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PersonalViewModel by viewModels()
    private var adapter: CompositeAdapter by Delegates.notNull()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPersonalBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setObservers()
    }

    private fun initialize() {
        initializeNavBar()
        setRecycler()
        setListeners()
    }

    private fun initializeNavBar() {
        binding.fragmentToolbar.toolbar.title = getString(R.string.menu_personal)
        binding.fragmentToolbar.toolbar.navigationIcon = null
    }

    private fun setRecycler() {
        adapter = CompositeAdapter
            .Builder()
            .add(PersonalDelegate(viewModel::onEditClick))
            .add(PersonalLoadingDelegate())
            .build()

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
    }

    private fun setListeners() {
        binding.addButton.setOnClickListener { viewModel.openAddDialog() }
        searchListener()
    }

    private fun setObservers() {
        viewModel.state.onEach(::handleState).collectOnStart(viewLifecycleOwner)
        viewModel.action.onEach(::handleActions).collectOnStart(viewLifecycleOwner)
    }

    private fun handleState(state: PersonalViewModel.State) {
        val list = PersonalListUiConverter().convertToPersonalListItem(state.dataList, state.isLoading)
        adapter.submitList(list)
    }

    private fun handleActions(action: PersonalViewModel.Actions) {
        when (action) {
            is PersonalViewModel.Actions.OpenAddDialog -> PersonalDialogFragment().show(childFragmentManager, tag)
            is PersonalViewModel.Actions.OpenEditDialog -> {
                val menuDialogFragment = PersonalDialogFragment()

                val args = Bundle()
                args.putParcelable(PersonalDialogFragment.PARCEL_ITEM, action.item)
                menuDialogFragment.arguments = args
                menuDialogFragment.show(
                    childFragmentManager,
                    menuDialogFragment.tag
                )
            }
        }
    }

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