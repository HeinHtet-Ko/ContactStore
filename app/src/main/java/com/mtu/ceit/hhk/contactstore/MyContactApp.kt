package com.mtu.ceit.hhk.contactstore

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType

import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.mtu.ceit.hhk.contactstore.features.ContactAdd
import com.mtu.ceit.hhk.contactstore.features.contactlist.ContactList


@ExperimentalFoundationApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@RequiresApi(Build.VERSION_CODES.N)
@ExperimentalAnimationApi
@Composable
    fun MyContactApp() {



    val navController = rememberAnimatedNavController()

    AnimatedNavHost(navController = navController, startDestination = Screen.ContactListScreen.route){

        composable(Screen.HomeScreen.route){
            Home()
        }
        composable(Screen.SplashScreen.route){

            SplashScreen(navController)

        }
        composable(Screen.ContactListScreen.route,
        exitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        },
        popEnterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
        }){

           ContactList(navController = navController)

        }

        composable(Screen.ContactDetailScreen.route
            , arguments = listOf(navArgument("contact_id"){type = NavType.LongType}),
        enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        },
        exitTransition = {
            Log.d("animtrack", "MyContactApp: JUSTEXIT")
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(700))
        },
        popExitTransition = {
            Log.d("animtrack", "MyContactApp: POPEXIT")
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
        }){ backstack ->

            val id = backstack.arguments?.getLong("contact_id")
            ContactDetail(navController=navController,id!!)


        }

        composable(route = Screen.ContactAddScreen.route){

            ContactAdd()

        }

    }

    }

