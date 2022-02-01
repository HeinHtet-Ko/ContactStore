package com.mtu.ceit.hhk.contactstore.domain.models

import com.alexstyl.contactstore.Label

data class ContactDetail(
    val id:Long? = null,
    val displayName:String?,
    val firstName:String?=null,
    val lastName:String?=null,
    val imgData:ByteArray?,
    val isStarred:Boolean,
    val phones:List<LabeledPhone>,
    val mails:List<LabeledMail>? = null,
    val note:String? = null,
    val webAddresses:List<LabeledWebAddress>? = null
)


data class LabeledWebAddress(
    val value: String,
    val label: Label
)
data class LabeledPhone(
    val value:String,
    val label:Label
)
data class LabeledMail(
    val value:String,
    val label:Label
)
