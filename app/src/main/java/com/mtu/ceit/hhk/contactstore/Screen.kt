package com.mtu.ceit.hhk.contactstore

sealed class Screen (val route:String){

    object Splash:Screen("splash_route")
    object Home:Screen("home_route")
    object MainScreen:Screen("main_route")
    object ContactDetailScreen:Screen("contact_detail_route")

}

