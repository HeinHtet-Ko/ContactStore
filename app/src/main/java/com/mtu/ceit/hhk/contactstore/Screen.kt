package com.mtu.ceit.hhk.contactstore

import com.mtu.ceit.hhk.contactstore.domain.models.ContactDetail

sealed class Screen (val route:String){

    object SplashScreen:Screen("splash")
    object HomeScreen:Screen("home")
    object ContactListScreen:Screen("contact_list")
    object ContactDetailScreen:Screen("{contact_id}/contact_detail")
    {
fun createRoute(contactID:Long) = "$contactID/contact_detail"
    }
    object ContactAddScreen:Screen("contact_add/?edit_contactID={edit_contactID}")
    {
        fun createRoute(updateContact:Long) = "contact_add/?edit_contactID=$updateContact"
    }


}

