package com.mtu.ceit.hhk.contactstore

sealed class Screen (val route:String){

    object SplashScreen:Screen("splash")
    object HomeScreen:Screen("home")
    object ContactListScreen:Screen("contact_list")
    object ContactDetailScreen:Screen("{contact_id}/contact_detail")
    {
fun createRoute(contactID:Long) = "$contactID/contact_detail"
    }


}

