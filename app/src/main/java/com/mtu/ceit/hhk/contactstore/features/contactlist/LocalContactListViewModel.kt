package com.mtu.ceit.hhk.contactstore.features.contactlist

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexstyl.contactstore.Contact

import com.mtu.ceit.hhk.contactstore.domain.ContactRepository
import com.mtu.ceit.hhk.contactstore.domain.DeleteContact
import com.mtu.ceit.hhk.contactstore.domain.GetAllContacts
import com.mtu.ceit.hhk.contactstore.domain.models.ContactDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LocalContactListViewModel @Inject constructor (
    private val getAllContacts: GetAllContacts,
    private val deleteContact: DeleteContact) :ViewModel() {

    private var _contactList:MutableState<List<Contact>> = mutableStateOf(mutableListOf())
    var searchResults:MutableState<List<Contact>> = mutableStateOf(mutableListOf())
    val contactList = _contactList



    init {
        getContacts()

    }



    fun deleteContacts(contactIdList:List<Long>) {

        viewModelScope.launch {
            deleteContact.invoke(contactIdList) }

    }

     private fun getContacts(){

        viewModelScope.launch() {

            getAllContacts.invoke(Unit).collect {
                _contactList.value = it
            }
        }
    }


}