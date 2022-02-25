package com.mtu.ceit.hhk.contactstore.domain.models

import com.alexstyl.contactstore.Label


data class ContactDetail(
    val id:Long? = null,
    val displayName:String? = null,
    val firstName:String?=null,
    val lastName:String?=null,
    val imgData:ByteArray? = null,
    val isStarred:Boolean = false,
    var phones:List<LabeledPhone>,
    val mails:List<LabeledMail>? = null,
    val note:String? = null,
    val webAddress:String? = null
)

data class LabeledPhone(
     val phoneValue:String,
     val label:Label
):LabeledValue<String>(phoneValue,label)


data class LabeledMail(
     val mailValue:String,
     val label:Label
): LabeledValue<String>(mailValue,label)

sealed class LabeledValue<T>(val value:T,val lab: Label)

