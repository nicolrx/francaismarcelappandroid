package com.example.francaisavecmarcel.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import com.example.francaisavecmarcel.R
import dev.hotwire.core.config.Hotwire
import dev.hotwire.core.turbo.config.PathConfiguration
import dev.hotwire.navigation.activities.HotwireActivity
import dev.hotwire.navigation.navigator.NavigatorConfiguration
import dev.hotwire.navigation.util.applyDefaultImeWindowInsets

const val baseURL = "http://10.0.2.2:3000"

class MainActivity : HotwireActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        findViewById<View>(R.id.main).applyDefaultImeWindowInsets()
    }

    override fun onStart() {
        super.onStart()
        handleDeepLink(intent)
    }

    override fun navigatorConfigurations() = listOf(
        NavigatorConfiguration(
            name = "main",
            startLocation = baseURL,
            navigatorHostId = R.id.home_nav_host
        )
    )

    private fun handleDeepLink(intent: Intent?) {
        val path = intent?.getStringExtra("path")
        path?.let {
            Log.d(TAG, "Handling deep link with path: $it")
            delegate.currentNavigator?.route("$baseURL$it")
        }
        this.intent = null
    }
}