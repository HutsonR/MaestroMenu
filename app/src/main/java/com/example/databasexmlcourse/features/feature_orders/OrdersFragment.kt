package com.example.databasexmlcourse.features.feature_orders

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
import com.example.databasexmlcourse.core.models.AlertData
import com.example.databasexmlcourse.core.utils.collectOnStart
import com.example.databasexmlcourse.databinding.FragmentHomeBinding
import com.example.databasexmlcourse.databinding.FragmentOrdersBinding
import com.example.databasexmlcourse.features.feature_orders.adapter.OrderDelegate
import com.example.databasexmlcourse.features.feature_orders.adapter.OrderLoadingDelegate
import com.example.databasexmlcourse.features.feature_orders.adapter.models.OrdersListUiConverter
import com.example.databasexmlcourse.features.feature_personal.PersonalViewModel
import com.example.databasexmlcourse.features.feature_personal.adapter.PersonalDelegate
import com.example.databasexmlcourse.features.feature_personal.adapter.PersonalLoadingDelegate
import com.example.databasexmlcourse.features.feature_personal.adapter.models.PersonalListUiConverter
import com.example.databasexmlcourse.features.feature_personal.dialogs.PersonalDialogFragment
import kotlinx.coroutines.flow.onEach
import kotlin.properties.Delegates

class OrdersFragment : BaseFragment() {
    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrdersViewModel by viewModels()
    private var adapter: CompositeAdapter by Delegates.notNull()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
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
        binding.fragmentToolbar.toolbar.title = getString(R.string.menu_orders)
        binding.fragmentToolbar.toolbar.navigationIcon = null
    }

    private fun setRecycler() {
        adapter = CompositeAdapter
            .Builder()
            .add(OrderDelegate(viewModel::onCancelClick, viewModel::onActionClick))
            .add(OrderLoadingDelegate())
            .build()

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
    }

    private fun setListeners() {
        binding.addButton.setOnClickListener { viewModel.openAddDialog() }
    }

    private fun setObservers() {
        viewModel.state.onEach(::handleState).collectOnStart(viewLifecycleOwner)
        viewModel.action.onEach(::handleActions).collectOnStart(viewLifecycleOwner)
    }

    private fun handleState(state: OrdersViewModel.State) {
        val list = OrdersListUiConverter().convertToOrdersListItem(state.dataList, state.isLoading)
        adapter.submitList(list)
    }

    private fun handleActions(action: OrdersViewModel.Actions) {
        when (action) {
            is OrdersViewModel.Actions.OpenAddDialog -> navigateTo(R.id.action_ordersFragment_to_orderAddFragment)
            is OrdersViewModel.Actions.ShowCancelAlert -> showAlert(
                AlertData(
                    title = R.string.alert_order_cancel_title,
                    message = R.string.alert_order_cancel_message,
                    positiveButton = R.string.alert_order_cancel_confirm,
                    isNegativeButtonNeeded = true,
                    navigate = { viewModel.onConfirmCancelClick(action.item) }
                )
            )
        }
    }

}