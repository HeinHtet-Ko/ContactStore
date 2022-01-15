package com.mtu.ceit.hhk.contactstore

import android.os.Build
import android.window.SplashScreen
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alexstyl.contactstore.ContactStore
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.flow.first


@ExperimentalFoundationApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@RequiresApi(Build.VERSION_CODES.N)
@ExperimentalAnimationApi
@Composable
    fun MyContactApp() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route){

        composable(Screen.MainScreen.route){
            MainScreen()
        }
        composable(Screen.Splash.route){

            SplashScreen(navController)

        }
        composable(Screen.Home.route){

            HomeScreen()

        }

        composable(Screen.ContactDetailScreen.route){
            ContactDetail()
        }

    }

    }

