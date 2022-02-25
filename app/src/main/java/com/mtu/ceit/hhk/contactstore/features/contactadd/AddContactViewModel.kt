package com.mtu.ceit.hhk.contactstore.features.contactadd

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringArrayResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexstyl.contactstore.Label
import com.alexstyl.contactstore.LabeledValue
import com.mtu.ceit.hhk.contactstore.R
import com.mtu.ceit.hhk.contactstore.domain.GetContactDetail
import com.mtu.ceit.hhk.contactstore.domain.PutContact
import com.mtu.ceit.hhk.contactstore.domain.UpdateContact
import com.mtu.ceit.hhk.contactstore.domain.models.ContactDetail
import com.mtu.ceit.hhk.contactstore.domain.models.LabeledMail
import com.mtu.ceit.hhk.contactstore.domain.models.LabeledPhone

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val putContact: PutContact,
    private val getContactDetail: GetContactDetail,
   private val updateContact: UpdateContact
):ViewModel() {



    val firstName = mutableStateOf("")
    val lastName = mutableStateOf("")

     var phoneList = mutableListOf<LabeledPhone>()
    val phoneCount = mutableStateOf(1)

     var mailList = mutableListOf<LabeledMail>()
    val mailCount = mutableStateOf(1)

//    val mailValue= mutableStateOf("")
//    val phoneValue = mutableStateOf("")

    val webAddress = mutableStateOf("")
    val note = mutableStateOf("")

    var update_contact = mutableStateOf<ContactDetail?>(null)

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

    fun getContactUpdate(id:Long){

        viewModelScope.launch {
            val contact = getContactDetail.invoke(id)
            update_contact.value = contact
            firstName.value = contact.firstName.toString()
            lastName.value = contact.lastName.toString()
            note.value = contact.note ?: ""
            webAddress.value = contact.webAddress ?: ""

            phoneList = contact.phones.toMutableList()
            phoneList.add(LabeledPhone("",Label.PhoneNumberMobile))
            phoneCount.value = phoneList.size

            mailList = contact.mails?.toMutableList() ?: mutableListOf()
            mailList.add(LabeledMail("",Label.LocationWork))
            mailCount.value = mailList.size

        }


    }

    fun addPhoneList(index:Int,labelPhone:LabeledPhone){

        if(phoneList.size == index){
            phoneList.add(index,labelPhone)
        }else{
            phoneList[index] = labelPhone
        }

        if(index+1 == phoneCount.value)
        {
            phoneCount.value = index +2
            if(update_contact.value != null){
                phoneList.add(LabeledPhone("",Label.PhoneNumberMobile))
            }
        }
    }


    fun addMailList(index:Int,labeledMail: LabeledMail) {

        if (mailList.size == index){
            mailList.add(index,labeledMail)
        }else {
            mailList[index] = labeledMail
        }

        if(index+1 == mailCount.value)
        {
            mailCount.value = index +2
            if(update_contact.value != null){
                mailList.add(LabeledMail("",Label.LocationWork))
            }
        }


    }

    fun addContact() {
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

    fun updateContact(contactID:Long) {
        viewModelScope.launch {
            phoneList.removeLast()
            mailList.removeLast()
            updateContact.invoke(
                ContactDetail(
                    id = contactID,
                    firstName = firstName.value,
                    lastName = lastName.value,
                    phones = phoneList,
                    mails = mailList,
                    webAddress = webAddress.value,
                    note = note.value
                )
            )
        }

    }

}


