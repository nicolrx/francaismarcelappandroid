package com.example.francaisavecmarcel.models

import androidx.annotation.IdRes
import com.example.francaisavecmarcel.R
import com.example.francaisavecmarcel.R.*

data class Tab(
    val name: String,
    val path: String,
    @IdRes val menuId: Int,
    @IdRes val navigatorHostId: Int
) {
    companion object {
        val all = listOf(
            Tab(
                name = "accueil",
                path = "/",
                menuId = id.bottom_nav_home,
                navigatorHostId = id.home_nav_host,
            ),
            Tab(
                name = "fiches",
                path = "/fiches",
                menuId = id.bottom_nav_fiches,
                navigatorHostId = id.fiches_nav_host,
            ),
            Tab(
                name = "pratique",
                path = "/pratique",
                menuId = id.bottom_nav_pratique,
                navigatorHostId = id.pratique_nav_host,
            ),
            Tab(
                name = "marcel",
                path = "/marcel",
                menuId = id.bottom_nav_marcel,
                navigatorHostId = id.marcel_nav_host,
            ),
            Tab(
                name = "compte",
                path = "/compte",
                menuId = id.bottom_nav_profile,
                navigatorHostId = id.profile_nav_host,
            )
        )
    }
}