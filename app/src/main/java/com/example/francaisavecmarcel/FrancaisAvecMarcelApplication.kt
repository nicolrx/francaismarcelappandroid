package com.example.francaisavecmarcel

import android.app.Application
import com.google.firebase.FirebaseApp
import com.example.francaisavecmarcel.activities.baseURL
import com.example.francaisavecmarcel.components.ButtonComponent
import com.example.francaisavecmarcel.components.NotificationTokenComponent
import com.example.francaisavecmarcel.fragments.WebFragment
import dev.hotwire.core.bridge.BridgeComponentFactory
import dev.hotwire.core.bridge.KotlinXJsonConverter
import dev.hotwire.core.config.Hotwire
import dev.hotwire.core.turbo.config.PathConfiguration
import dev.hotwire.navigation.config.registerBridgeComponents
import dev.hotwire.navigation.config.registerFragmentDestinations

class FrancaisAvecMarcelApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        Hotwire.loadPathConfiguration(
            context = this,
            location = PathConfiguration.Location(
                remoteFileUrl = "$baseURL/configurations/android_v1.json"
            )
        )

        Hotwire.registerFragmentDestinations(
            WebFragment::class,
        )

        Hotwire.registerBridgeComponents(
            BridgeComponentFactory("button", ::ButtonComponent),
            BridgeComponentFactory(
                "notification-token",
                ::NotificationTokenComponent
            )
        )

        Hotwire.config.jsonConverter = KotlinXJsonConverter()
    }
}