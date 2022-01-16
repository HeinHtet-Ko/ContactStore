package com.mtu.ceit.hhk.contactstore.data

import com.alexstyl.contactstore.ContactStore
import com.mtu.ceit.hhk.contactstore.domain.Contact
import com.mtu.ceit.hhk.contactstore.domain.ContactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ContactReposImpl @Inject constructor(val contactStore: ContactStore):ContactRepository {
    override suspend fun getAllContacts(): Flow<List<Contact>> {

        return contactStore.fetchContacts().map {
            it.map {
                Contact(it.contactId,it.displayName,it.isStarred)
            }
        }


    }

    override suspend fun getAllContactsDetails() {
        TODO("Not yet implemented")
    }

    override fun insertContact() {
        TODO("Not yet implemented")
    }


}