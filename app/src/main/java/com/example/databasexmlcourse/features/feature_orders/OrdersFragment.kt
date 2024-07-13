package com.example.databasexmlcourse.features.feature_orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.databasexmlcourse.R
import com.example.databasexmlcourse.core.BaseFragment
import com.example.databasexmlcourse.databinding.FragmentHomeBinding
import com.example.databasexmlcourse.databinding.FragmentOrdersBinding

class OrdersFragment : BaseFragment() {
    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeNavBar()
    }

    private fun initializeNavBar() {
        binding.fragmentToolbar.toolbar.title = getString(R.string.menu_orders)
    }

}