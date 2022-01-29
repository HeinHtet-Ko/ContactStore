package com.mtu.ceit.hhk.contactstore.features.contactlist

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mtu.ceit.hhk.contactstore.domain.models.Contact
import com.mtu.ceit.hhk.contactstore.domain.ContactRepository
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
class LocalContactListViewModel @Inject constructor (val repos: ContactRepository):ViewModel() {


    private val _contactDetail:MutableState<ContactDetail?> = mutableStateOf(null)
    val contactDetail get() = _contactDetail

    var _contactList:MutableState<List<Contact>> = mutableStateOf(mutableListOf())
    var searchResults:MutableState<List<Contact>> = mutableStateOf(mutableListOf())
    val contactList = _contactList

    var query = mutableStateOf("")

    init {
        Log.d("Allcontacttrack", "getAllContacts: vm $this")
        Log.d("Allcontacttrack", "getAllContacts: vm $repos")
        viewModelScope.launch {
            getContacts()
          // repos.insertContact()
        }

    }

    fun onQuChange(query:String){
        searchResults.value = _contactList.value.filter { it.name!!.contains(query) }
        Log.d("trackerlist", "onQuChange: ${_contactList.value}")
    }

    override fun onCleared() {
        Log.d("Allcontacttrack", "on cleared $this")
        super.onCleared()
    }

    fun getContactDetail(id:Long) {
        viewModelScope.launch {
          _contactDetail.value =  repos.getContactDetail(id)
        }

    }

    fun getContacts(){

        viewModelScope.launch() {

              repos.getAllContacts().collect {
                  _contactList.value = it + it
            }
//
//            repos.getAllContacts().collect {
//
//                _contactList.value = it
//
//
//            }


        }
    }


}