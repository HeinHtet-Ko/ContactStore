package com.mtu.ceit.hhk.contactstore.domain.models

import com.alexstyl.contactstore.Label

data class ContactDetail(
    val id:Long? = null,
    val displayName:String? = null,
    val firstName:String?=null,
    val lastName:String?=null,
    val imgData:ByteArray? = null,
    val isStarred:Boolean = false,
    val phones:List<LabeledPhone>,
    val mails:List<LabeledMail>? = null,
    val note:String? = null,
    val webAddress:String? = null
)

data class LabeledPhone(
    val value:String,
    val label:Label
)
data class LabeledMail(
    val value:String,
    val label:Label
)
