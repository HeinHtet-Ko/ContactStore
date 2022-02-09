package com.mtu.ceit.hhk.contactstore.features

import com.alexstyl.contactstore.Label

fun String.toLabel(): Label {

    return when(this) {
        "Mobile" -> {
            Label.PhoneNumberMobile
        }
        "Home" -> {
            Label.LocationHome
        }
        "Work" -> {
            Label.LocationWork
        }
        "Other" -> {
            Label.Other
        }
        else -> {
            Label.Other

        }
    }
}

fun Label.toStringValue():String{

    var name = this::class.simpleName ?: ""
     when {
        name.contains("Location") -> name = name.substringAfter("Location")
        name.contains("PhoneNumber") -> name = name.substringAfter("PhoneNumber")
        else -> name
    }

   return name

}