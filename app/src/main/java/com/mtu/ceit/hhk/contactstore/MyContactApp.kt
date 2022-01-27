package com.mtu.ceit.hhk.contactstore

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.ContactListScreen.route){

        composable(Screen.HomeScreen.route){
            Home()
        }
        composable(Screen.SplashScreen.route){

            SplashScreen(navController)

        }
        composable(Screen.ContactListScreen.route){

           ContactList(navController = navController)

        }

        composable(Screen.ContactDetailScreen.route
            , arguments = listOf(navArgument("contact_id"){type = NavType.LongType})){ backstack ->

            val id = backstack.arguments?.getLong("contact_id")
            Log.d("contactidnavigate", "MyContactApp: $id")

                ContactDetail(navController=navController,id!!)


        }

        composable(route = Screen.ContactAddScreen.route){

            ContactAdd()

        }

    }

    }

