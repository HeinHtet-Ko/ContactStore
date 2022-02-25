package com.mtu.ceit.hhk.contactstore.domain


import com.alexstyl.contactstore.Contact
import com.mtu.ceit.hhk.contactstore.domain.models.ContactDetail
import kotlinx.coroutines.flow.Flow

interface ContactRepository {

    suspend fun getAllContacts():Flow<List<Contact>>

    suspend fun getAllContactsDetails()

    suspend fun insertContact(contactDetail: ContactDetail)

    suspend fun deleteContact(contactIdList:List<Long>)

    suspend fun getContactDetail(contact_id:Long):ContactDetail

    suspend fun toggleFavourite(contact_id:Long)

    suspend fun updateContact(contact:ContactDetail)
}