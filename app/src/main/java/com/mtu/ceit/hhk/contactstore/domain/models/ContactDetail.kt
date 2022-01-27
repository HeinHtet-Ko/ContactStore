package com.mtu.ceit.hhk.contactstore.domain.models

import com.alexstyl.contactstore.Label

data class ContactDetail(
    val id:Long,
    val name:String?,
    val imgData:ByteArray?,
    val isStarred:Boolean,
    val phones:List<LabeledPhone>,
    val mails:List<LabeledMail>? = null,

)



data class LabeledPhone(
    val value:String,
    val label:Label
)
data class LabeledMail(
    val value:String,
    val label:Label
)
