package com.mtu.ceit.hhk.contactstore

import android.window.SplashScreen
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alexstyl.contactstore.ContactStore
import kotlinx.coroutines.flow.first


@ExperimentalAnimationApi
@Composable
    fun MyContactApp(store:ContactStore) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route){

        composable(Screen.Splash.route){

            SplashScreen(navController)

        }
        composable(Screen.Home.route){

            HomeScreen()

        }

    }

    }

