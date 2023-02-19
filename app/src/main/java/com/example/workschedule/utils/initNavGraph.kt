package com.example.workschedule.utils

import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import com.example.workschedule.R
import com.google.android.material.navigation.NavigationView

fun initNavGraph(
    navView: NavigationView,
    drawerLayout: DrawerLayout,
    navController: NavController
) {
    navView.setNavigationItemSelectedListener {
        if (navController.currentDestination?.id != it.itemId) {
            drawerLayout.close()
            when (it.itemId) {

                R.id.nav_main -> {
                    when (navController.currentDestination?.id) {
                        R.id.nav_drivers ->
                            navController.navigate(R.id.action_nav_drivers_to_nav_main)
                    }
                    when (navController.currentDestination?.id) {
                        R.id.nav_trains ->
                            navController.navigate(R.id.action_nav_trains_to_nav_main)
                    }
                    when (navController.currentDestination?.id) {
                        R.id.nav_joint_schedule ->
                            navController.navigate(R.id.action_nav_joint_to_nav_main)
                    }
                }

                R.id.nav_drivers -> {
                    when (navController.currentDestination?.id) {
                        R.id.nav_main ->
                            navController.navigate(R.id.action_nav_main_to_nav_drivers)
                    }
                    when (navController.currentDestination?.id) {
                        R.id.nav_trains ->
                            navController.navigate(R.id.action_nav_trains_to_nav_drivers)
                    }
                    when (navController.currentDestination?.id) {
                        R.id.nav_joint_schedule ->
                            navController.navigate(R.id.action_nav_joint_to_nav_drivers)
                    }
                }

                R.id.nav_trains -> {
                    when (navController.currentDestination?.id) {
                        R.id.nav_main ->
                            navController.navigate(R.id.action_nav_main_to_nav_trains)
                    }
                    when (navController.currentDestination?.id) {
                        R.id.nav_drivers ->
                            navController.navigate(R.id.action_nav_drivers_to_nav_trains)
                    }
                    when (navController.currentDestination?.id) {
                        R.id.nav_joint_schedule ->
                            navController.navigate(R.id.action_nav_joint_to_nav_trains)
                    }
                }

                R.id.nav_joint_schedule -> {
                    when (navController.currentDestination?.id) {
                        R.id.nav_main ->
                            navController.navigate(R.id.action_nav_main_to_joint_schedule)
                    }
                    when (navController.currentDestination?.id) {
                        R.id.nav_trains ->
                            navController.navigate(R.id.action_nav_trains_to_joint_schedule)
                    }
                    when (navController.currentDestination?.id) {
                        R.id.nav_drivers ->
                            navController.navigate(R.id.action_nav_drivers_to_joint_schedule)
                    }
                }
            }
            true
        } else false
    }
}
