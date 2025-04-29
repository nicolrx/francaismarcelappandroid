package com.example.francaisavecmarcel.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.francaisavecmarcel.R
import com.example.francaisavecmarcel.models.Tab
import dev.hotwire.core.config.Hotwire
import dev.hotwire.core.turbo.config.PathConfiguration
import dev.hotwire.navigation.activities.HotwireActivity
import dev.hotwire.navigation.navigator.NavigatorConfiguration
import dev.hotwire.navigation.util.applyDefaultImeWindowInsets

const val baseURL = "http://10.0.2.2:3000"

class MainActivity : HotwireActivity() {
    private val tabs = Tab.all
    private lateinit var bottomNav: BottomNavigationView
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        findViewById<View>(R.id.main).applyDefaultImeWindowInsets()

        bottomNav = findViewById(R.id.bottom_nav)
        bottomNav.setOnItemSelectedListener { tab ->
            val selectedTab = tabs.first { it.menuId == tab.itemId }
            Log.d(TAG, "Tab selected: ${selectedTab.name} (${selectedTab.path})")
            showTab(selectedTab)
            true
        }
        showTab(tabs.first())
    }

    override fun onStart() {
        super.onStart()
        handleDeepLink(intent)
    }

    override fun navigatorConfigurations() = tabs.map { tab ->
        Log.d(TAG, "Registering navigator configuration for ${tab.name} at ${tab.path}")
        NavigatorConfiguration(
            name = tab.name,
            startLocation = "$baseURL/${tab.path}",
            navigatorHostId = tab.navigatorHostId
        )
    }

    private fun showTab(tab: Tab) {
        Log.d(TAG, "Showing tab: ${tab.name} (${tab.path})")
        tabs.forEach {
            val view = findViewById<View>(it.navigatorHostId)
            view.visibility = if (it == tab) View.VISIBLE else View.GONE
        }
    }

    private fun handleDeepLink(intent: Intent?) {
        val path = intent?.getStringExtra("path")
        path?.let {
            Log.d(TAG, "Handling deep link with path: $it")
            delegate.currentNavigator?.route("$baseURL$it")
        }
        this.intent = null
    }

    fun setBottomNavigationVisibility(visible: Boolean) {
        Log.d(TAG, "Setting bottom navigation visibility to: $visible")
        bottomNav.visibility = if (visible) View.VISIBLE else View.GONE
    }
}