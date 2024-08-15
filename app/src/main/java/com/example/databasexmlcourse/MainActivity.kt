package com.example.databasexmlcourse

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.databasexmlcourse.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setNavigation()
    }

    private fun setNavigation() {
        val bottomNavigationView: BottomNavigationView = binding.bottomNavigation
        navController = (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.itemActiveIndicatorColor = getColorStateList(R.color.bgNavActiveItem)
        checkActiveNavigation()
    }

    private fun checkActiveNavigation() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val shouldShowNavigation = when (destination.id) {
                R.id.homeFragment,
                R.id.personalFragment,
                R.id.menuFragment,
                R.id.ordersFragment,
                R.id.profileFragment -> true
                else -> false
            }

            if (shouldShowNavigation) {
                binding.bottomNavigationDivider.visibility = View.VISIBLE
                binding.bottomNavigation.visibility = View.VISIBLE
            } else {
                binding.bottomNavigationDivider.visibility = View.GONE
                binding.bottomNavigation.visibility = View.GONE
            }
        }
    }


}