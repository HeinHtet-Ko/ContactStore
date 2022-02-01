package com.mtu.ceit.hhk.contactstore.features

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.alexstyl.contactstore.Label
import com.mtu.ceit.hhk.contactstore.domain.models.LabeledMail
import com.mtu.ceit.hhk.contactstore.domain.models.LabeledPhone
import com.mtu.ceit.hhk.contactstore.domain.models.LabeledWebAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddContactViewModel @Inject constructor():ViewModel() {

    val firstName = mutableStateOf("")
    val lastName = mutableStateOf("")

    val phoneList = mutableListOf<LabeledPhone>()
    var phoneCount = mutableStateOf(1)

    val mailList = mutableListOf<LabeledMail>()
    var mailCount = mutableStateOf(1)

    val webList = mutableListOf<LabeledWebAddress>()
    var webCount = mutableStateOf(1)


    fun onFirstNameChange(fName:String){

        firstName.value = fName

    }

    fun onLastNameChange(lName:String){

        lastName.value = lName
    }

    fun addPhoneList(index:Int,labelPhone:LabeledPhone){
      //  phoneList.add(index,labelPhone)
        phoneCount.value  = index+2
    }

    fun onTestAdd(index: Int,str:String) {


        if(phoneList.size == index+1)
        {
           // phoneList[index] = str
        }else{
           // testStringList.add(index,str)
        }





       // phoneList[index] = LabeledPhone(str,Label.PhoneNumberMobile)
       // phoneList.add(index, LabeledPhone(str, Label.PhoneNumberMobile))
      //  testStringList[index-1] = str
    //    testStringList.add(index,str)
        phoneCount.value = index+2

    }



}