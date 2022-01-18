package com.mtu.ceit.hhk.contactstore

sealed class Screen (val route:String){

    object SplashScreen:Screen("splash_route")
    object HomeScreen:Screen("home_route")
    object ContactListScreen:Screen("contact_list_route")
    object ContactDetailScreen:Screen("contact_detail_route")

}

