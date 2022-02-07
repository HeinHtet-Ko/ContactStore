package com.mtu.ceit.hhk.contactstore.features.contactadd

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringArrayResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexstyl.contactstore.Label
import com.mtu.ceit.hhk.contactstore.R
import com.mtu.ceit.hhk.contactstore.domain.PutContact
import com.mtu.ceit.hhk.contactstore.domain.models.ContactDetail
import com.mtu.ceit.hhk.contactstore.domain.models.LabeledMail
import com.mtu.ceit.hhk.contactstore.domain.models.LabeledPhone

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val putContact: PutContact
):ViewModel() {



    private val firstName = mutableStateOf("")
    val lastName = mutableStateOf("")

    private val phoneList = mutableListOf<LabeledPhone>()
    val phoneCount = mutableStateOf(1)

    private val mailList = mutableListOf<LabeledMail>()
    val mailCount = mutableStateOf(1)

    private val webAddress = mutableStateOf("")
    val note = mutableStateOf("")

    fun onFirstNameChange(fName:String){
        firstName.value = fName
    }

    fun onLastNameChange(lName:String){
        lastName.value = lName
    }

    fun onWebAddChange(web:String) {
        webAddress.value = web
    }

    fun onNoteChange(note:String) {
        this.note.value = note
    }

    fun addPhoneList(index:Int,labelPhone:LabeledPhone){
       // val labelPhone = LabeledPhone(phone,phoneLabel.value)
        if(phoneList.size == index){
            phoneList.add(index,labelPhone)
        }else{
            phoneList[index] = labelPhone
        }

        if(index+1 == phoneCount.value)
            phoneCount.value = index +2
         }


    fun addMailList(index:Int,labeledMail: LabeledMail) {
        Log.d("labelmailtracker", "ddaddMailList: ${labeledMail.label}")
        if (mailList.size == index){
            mailList.add(index,labeledMail)
        }else {
            mailList[index] = labeledMail
        }

        if(index+1 == mailCount.value)
        mailCount.value = index +2

    }

    fun addContact() {
        phoneList.forEach {
            Log.d("labeltracker", "addContacter: ${it.value} ${it.label}")
        }
        viewModelScope.launch {
            putContact.invoke(ContactDetail(
                firstName = firstName.value,
                lastName = lastName.value,
                phones = phoneList,
                mails = mailList,
                webAddress = webAddress.value,
                note = note.value
            ))
        }
    }

}

fun String.toLabel():Label {

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