package com.example.francaisavecmarcel.fragments

import android.Manifest
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import com.example.francaisavecmarcel.viewmodels.NotificationTokenViewModel
import com.example.francaisavecmarcel.activities.MainActivity
import dev.hotwire.navigation.destinations.HotwireDestinationDeepLink
import dev.hotwire.navigation.fragments.HotwireWebFragment
import kotlinx.coroutines.launch
import com.example.francaisavecmarcel.activities.baseURL

@HotwireDestinationDeepLink("hotwire://fragment/web")
class WebFragment : HotwireWebFragment() {
    private val viewModel = NotificationTokenViewModel()

    private val contract = ActivityResultContracts.RequestPermission()
    private val requestPermissionLauncher =
        registerForActivityResult(contract) { isGranted ->
            if (isGranted) registerForTokenChanges()
        }

    override fun onResume() {
        super.onResume()
        
        // Get the current location from arguments
        val location = arguments?.getString("location") ?: ""
        
        // Extract the path from the location
        val currentPath = if (location.startsWith(baseURL)) {
            location.substring(baseURL.length)
        } else {
            "unknown"
        }
        
        // Check if path matches any of our target paths
        val shouldHideNavBar = when {
            currentPath.startsWith("/users/") -> true
            currentPath.startsWith("/quizzes/") -> true
            currentPath == "/onboarding" -> true
            currentPath.startsWith("/dictees/") -> true
            else -> false
        }
        
        val mainActivity = activity as? MainActivity
        if (mainActivity != null) {
            mainActivity.setBottomNavigationVisibility(!shouldHideNavBar)
        }
    }

    fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            requestPermissionLauncher.launch(permission)
        }
    }

    private fun registerForTokenChanges() {
        val firebase = FirebaseMessaging.getInstance()
        firebase.token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.registerToken(task.result)
                }
            }
        }
    }
}