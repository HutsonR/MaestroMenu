package com.example.databasexmlcourse.features.feature_orders.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databasexmlcourse.R
import com.example.databasexmlcourse.core.BaseFragment
import com.example.databasexmlcourse.core.composite.adapter.CompositeAdapter
import com.example.databasexmlcourse.core.utils.collectOnStart
import com.example.databasexmlcourse.databinding.DialogFragmentOrdersBinding
import com.example.databasexmlcourse.features.feature_orders.add.adapter.OrderAddDelegate
import com.example.databasexmlcourse.features.feature_orders.add.adapter.OrderAddLoadingDelegate
import com.example.databasexmlcourse.features.feature_orders.add.adapter.models.OrderDishItemUiConverter
import com.example.databasexmlcourse.features.common.dialogs.DialogSearcherFragment
import kotlinx.coroutines.flow.onEach
import kotlin.properties.Delegates

class OrderAddFragment: BaseFragment() {
    private var _binding: DialogFragmentOrdersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrderAddViewModel by viewModels()
    private var adapter: CompositeAdapter by Delegates.notNull()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogFragmentOrdersBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeNavBar()
        initialize()
        setObservers()
    }

    private fun initializeNavBar() {
        binding.fragmentToolbar.toolbar.title = getString(R.string.menu_menu)
        binding.fragmentToolbar.toolbar.setNavigationOnClickListener { viewModel.goBack() }
    }

    private fun initialize() {
        with(binding) {
            saveButton.text = getString(R.string.orders_add_button)
            saveButton.setOnClickListener { viewModel.onActionButtonClick() }
            tableButton.setOnClickListener { viewModel.onTableButtonClick() }
            categoryButton.setOnClickListener { viewModel.onCategoryButtonClick() }
        }

        setRecycler()
        setListeners()
    }

    private fun setObservers() {
        viewModel.state.onEach(::handleState).collectOnStart(viewLifecycleOwner)
        viewModel.action.onEach(::handleActions).collectOnStart(viewLifecycleOwner)
    }

    private fun handleState(state: OrderAddViewModel.State) {
        val list = OrderDishItemUiConverter().convertToOrdersListItem(state.dishList, state.isLoading)
        adapter.submitList(list)
        updateSum(state.currentOrder?.totalAmount ?: 0)
        updateSaveButtonState(state.isSaveButtonEnable)
    }

    private fun handleActions(action: OrderAddViewModel.Actions) {
        when (action) {
            is OrderAddViewModel.Actions.OpenCategoryDialog -> {
                DialogSearcherFragment(viewModel.categoryList) {
                    Log.d("debugTag", "order add fragment: $it")
                }.show(childFragmentManager, "add order category view fragment")
            }
            is OrderAddViewModel.Actions.OpenTableDialog -> {
                DialogSearcherFragment(viewModel.tableList) {
                    Log.d("debugTag", "order add fragment: $it")
                }.show(childFragmentManager, "add order table view fragment")
            }
            is OrderAddViewModel.Actions.GoBack -> popBackStack()
        }
    }

    private fun setRecycler() {
        adapter = CompositeAdapter
            .Builder()
            .add(OrderAddDelegate(viewModel::onRemoveDishClick, viewModel::onAddDishClick))
            .add(OrderAddLoadingDelegate())
            .build()

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
    }


    private fun setListeners() {

    }

    private fun updateSaveButtonState(isEnabled: Boolean) {
        binding.saveButton.isEnabled = isEnabled
        binding.saveButton.alpha = if (isEnabled) 1.0f else 0.5f
    }

    private fun updateSum(sum: Int) {
        binding.amount.text = "Сумма: $sum ₽"
    }

    //    Listeners

}