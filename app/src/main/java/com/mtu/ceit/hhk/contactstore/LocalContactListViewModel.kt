package com.mtu.ceit.hhk.contactstore

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtu.ceit.hhk.contactstore.domain.Contact
import com.mtu.ceit.hhk.contactstore.domain.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LocalContactListViewModel @Inject constructor (val repos: ContactRepository):ViewModel() {


     var _contactList:MutableStateFlow<List<Contact>> = MutableStateFlow(mutableListOf())
    val contactList = _contactList

    fun getContacts(){
        viewModelScope.launch(IO) {
            repos.getAllContacts().collect {

                _contactList.value = it


            }
        }
    }


}