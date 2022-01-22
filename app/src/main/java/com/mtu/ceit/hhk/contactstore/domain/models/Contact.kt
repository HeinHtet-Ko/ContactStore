package com.mtu.ceit.hhk.contactstore.domain.models

data class Contact(
    val id:Long,
    var name:String?,
    val isStarred:Boolean
) {
}