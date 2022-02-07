package com.mtu.ceit.hhk.contactstore.features.contactdetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtu.ceit.hhk.contactstore.domain.GetContactDetail
import com.mtu.ceit.hhk.contactstore.domain.ToggleFavourite
import com.mtu.ceit.hhk.contactstore.domain.models.ContactDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactDetailViewModel @Inject constructor(
    private val getContactDetail: GetContactDetail,
    private val toggleFavourite: ToggleFavourite
):ViewModel(){


    private val _contactDetail:MutableState<ContactDetail?> = mutableStateOf(null)

    val contactDetail = _contactDetail

    fun fetchContactDetail(contactID:Long){

        viewModelScope.launch {
            _contactDetail.value = getContactDetail.invoke(contactID)
        }

    }

    fun toggleFav(id:Long){
        viewModelScope.launch {
            toggleFavourite.invoke(id)
           _contactDetail.value = getContactDetail.invoke(id)

        }

    }





}