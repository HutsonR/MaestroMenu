package com.example.databasexmlcourse.features.feature_menu

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databasexmlcourse.R
import com.example.databasexmlcourse.core.BaseFragment
import com.example.databasexmlcourse.core.composite.adapter.CompositeAdapter
import com.example.databasexmlcourse.core.utils.collectOnStart
import com.example.databasexmlcourse.databinding.FragmentMenuBinding
import com.example.databasexmlcourse.features.feature_menu.adapter.LoadingDelegate
import com.example.databasexmlcourse.features.feature_menu.adapter.MenuCategoryDelegate
import com.example.databasexmlcourse.features.feature_menu.adapter.MenuDelegate
import com.example.databasexmlcourse.features.feature_menu.adapter.MenuListUiConverter
import com.example.databasexmlcourse.features.feature_menu.dialogs.MenuDialogFragment
import com.example.databasexmlcourse.features.feature_menu.dialogs.MenuDialogRecyclerFragment.Companion.KEY_CATEGORY_ITEM
import com.example.databasexmlcourse.features.feature_menu.dialogs.MenuDialogRecyclerFragment.Companion.REQ_KEY_CATEGORY_ITEM
import com.example.databasexmlcourse.features.feature_menu.dialogs.MenuDialogRecyclerViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.properties.Delegates

class MenuFragment : BaseFragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MenuViewModel by viewModels()
    private var adapter: CompositeAdapter by Delegates.notNull()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
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
        binding.fragmentToolbar.toolbar.title = getString(R.string.menu_menu)
    }

    private fun setRecycler() {
        adapter = CompositeAdapter
            .Builder()
            .add(MenuDelegate(viewModel::onEditClick))
            .add(LoadingDelegate())
            .build()

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
    }

    private fun setListeners() {
        binding.addButton.setOnClickListener { viewModel.openDialog() }
        searchListener()
    }

    private fun setObservers() {
        viewModel.state.onEach(::handleState).collectOnStart(viewLifecycleOwner)
        viewModel.action.onEach(::handleActions).collectOnStart(viewLifecycleOwner)
    }

    private fun handleState(state: MenuViewModel.State) {
        val list = MenuListUiConverter().convertToMenuListItem(state.dataList, state.isLoading)
        adapter.submitList(list)
    }

    private fun handleActions(action: MenuViewModel.Actions) {
        when (action) {
            is MenuViewModel.Actions.OpenAddDishDialog -> MenuDialogFragment().show(childFragmentManager, tag)
            is MenuViewModel.Actions.OpenEditDishDialog -> {
                val menuDialogFragment = MenuDialogFragment()

                val args = Bundle()
                args.putParcelable(MenuDialogFragment.PARCEL_ITEM, action.item)
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