package com.mtu.ceit.hhk.contactstore.domain

import com.mtu.ceit.hhk.contactstore.domain.models.Contact
import com.mtu.ceit.hhk.contactstore.domain.models.ContactDetail
import kotlinx.coroutines.flow.Flow

interface ContactRepository {

    suspend fun getAllContacts():Flow<List<Contact>>

    suspend fun getAllContactsDetails()

    suspend fun insertContact(contactDetail: ContactDetail)

    suspend fun getContactDetail(contact_id:Long):ContactDetail
}