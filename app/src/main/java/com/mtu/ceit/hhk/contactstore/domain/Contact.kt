package com.mtu.ceit.hhk.contactstore.domain

data class Contact(
    val id:Long,
    var name:String?,
    val isStarred:Boolean
) {
}