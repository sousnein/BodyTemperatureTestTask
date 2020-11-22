package com.sousnein.bodytemperaturetesttask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sousnein.bodytemperaturetesttask.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation() {
        val bottomNavigationMenu = findViewById<BottomNavigationView>(R.id.bottomNavigationMenu)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        NavigationUI.setupWithNavController(bottomNavigationMenu, navHostFragment.navController)
    }
}
