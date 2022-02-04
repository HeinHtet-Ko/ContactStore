package com.mtu.ceit.hhk.contactstore.data

import android.util.Log
import com.alexstyl.contactstore.*
import com.mtu.ceit.hhk.contactstore.domain.models.Contact
import com.mtu.ceit.hhk.contactstore.domain.ContactRepository
import com.mtu.ceit.hhk.contactstore.domain.models.ContactDetail
import com.mtu.ceit.hhk.contactstore.domain.models.LabeledMail
import com.mtu.ceit.hhk.contactstore.domain.models.LabeledPhone
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactReposImpl @Inject constructor(val contactStore: ContactStore):ContactRepository {
    override suspend fun getAllContacts(): Flow<List<Contact>> {

        Log.d("Allcontacttrack", "getAllContacts: init $this")
        return contactStore.fetchContacts(
            columnsToFetch = listOf(ContactColumn.Mails,ContactColumn.Image),
//            predicate = ContactPredicate.ContactLookup(
//                inContactIds = listOf(2)
//            )
        ).map {
            it.map {

              //  Log.d("contactidnavigate", "altgetAllContacts: ${it.mails[0]}")
                Contact(it.contactId,it.displayName,it.isStarred)
            }
        }

    }

    override suspend fun getAllContactsDetails() {

    }

    override suspend fun insertContact(contactDetail: ContactDetail) {

        Log.d("insert", "insertContact: ${contactDetail.firstName} ${contactDetail.lastName}")
        contactStore.execute {
            insert {

                contactDetail.firstName?.let {
                    firstName = it
                }

                contactDetail.lastName?.let {
                    lastName = it
                }

                contactDetail.phones.forEach {
                    phone( it.value,it.label )
                }
                contactDetail.mails?.forEach {
                    mail(it.value,it.label)
                }

               // webAddress("ahsd",Label.Other)


                note = contactDetail.note

                contactDetail.webAddress?.let { webAddress(it,Label.LocationHome) }

              //  imageData = ImageData(byteArrayOf("sjsjd".toByte()))

            }
        }
    }

    override suspend fun getContactDetail(contact_id: Long):ContactDetail {


       val contact =  contactStore.fetchContacts(
            predicate = ContactPredicate.ContactLookup(
                inContactIds = listOf(contact_id)
            ),
           columnsToFetch = listOf(
               ContactColumn.Phones,
               ContactColumn.Mails,
               ContactColumn.Image,

           )
        ).first().first()
//        contact.phones.forEach {
//
//            val cs= it.label.toString()
//            val he = cs.substringBefore("@").substringAfter("$")
//            val final = if(he.contains("Location")) he.substringAfter("Location") else if(he.contains("PhoneNumber")) he.substringAfter("PhoneNumber") else "Others"
//            Log.d("contactdetailstructure", "getContactDetail: ")
//
//        }

       // contact.imageData.raw

        val phones:List<LabeledPhone> = contact.phones.map {
            LabeledPhone(it.value.raw,it.label)
        }

        val mails:List<LabeledMail> = contact.mails.map {
            LabeledMail(it.value.raw,it.label)
        }

        return ContactDetail(
            id = contact.contactId,
            displayName = contact.displayName,
            imgData = contact.imageData?.raw,
            isStarred = contact.isStarred,
            phones = phones,
            mails = mails)

    }


}