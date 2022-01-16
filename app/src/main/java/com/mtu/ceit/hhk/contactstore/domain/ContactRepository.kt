package com.mtu.ceit.hhk.contactstore.domain

import kotlinx.coroutines.flow.Flow

interface ContactRepository {

    suspend fun getAllContacts():Flow<List<Contact>>

    suspend fun getAllContactsDetails()

    fun insertContact()

}