package com.mtu.ceit.hhk.contactstore.data

import android.util.Log
import androidx.compose.ui.text.toUpperCase
import com.alexstyl.contactstore.*

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
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class ContactReposImpl @Inject constructor(val contactStore: ContactStore):ContactRepository {
    override suspend fun getAllContacts(): Flow<List<Contact>> {

        return contactStore.fetchContacts()
//        return contactStore.fetchContacts(
//         //   columnsToFetch = listOf(ContactColumn.Mails,ContactColumn.Image),
////            predicate = ContactPredicate.ContactLookup(
////                inContactIds = listOf(2)
////            )
//        ).map {
//            it.map {
//
//              //  Log.d("contactidnavigate", "altgetAllContacts: ${it.mails[0]}")
//                Contact(it.contactId,it.displayName,it.isStarred)
//            }
//        }

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
                note = contactDetail.note

                contactDetail.webAddress?.let { webAddress(it,Label.WebsiteHomePage) }

            }
        }
    }


    override suspend fun deleteContact(contactIdList: List<Long>) {




            contactIdList.forEach {
                withContext(IO){
                    contactStore.execute {
                        delete(it)
                    }
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
               ContactColumn.WebAddresses,
               ContactColumn.Note,
               ContactColumn.Names

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


        val webAdd = if(contact.webAddresses.isNotEmpty()) contact.webAddresses.first() else null

        val note:String? = contact.note?.raw


        return ContactDetail(
            id = contact.contactId,
            displayName = contact.displayName,
            firstName = contact.firstName + " ${contact.middleName}",
            lastName = contact.lastName,
            imgData = contact.imageData?.raw,
            isStarred = contact.isStarred,
            phones = phones,
            mails = mails,
            webAddress = webAdd?.value?.raw,
            note = note)

    }

    override suspend fun toggleFavourite(contact_id: Long) {

        val contacts = contactStore.fetchContacts(
           predicate = ContactPredicate.ContactLookup(inContactIds = listOf(contact_id))
        ).first()
        if(contacts.isEmpty()) return // no found contacts

        val contact = contacts.first()



        contactStore.execute {
            update(contact.mutableCopy().apply {
                isStarred = !isStarred

            })
        }


    }

    override suspend fun updateContact(contactDetail: ContactDetail) {

       val contact = contactStore.fetchContacts(
            predicate = ContactPredicate.ContactLookup( inContactIds = listOf(contactDetail.id!!) ),
                    columnsToFetch = listOf(
                        ContactColumn.Phones,
                        ContactColumn.Mails,
                        ContactColumn.Image,
                        ContactColumn.WebAddresses,
                        ContactColumn.Note,
                        ContactColumn.Names)).first().first()


      val updatedFirstName = contactDetail.firstName
      val updatedLastName = contactDetail.lastName
      val updatedPhones =  contactDetail.phones.map {
            LabeledValue(PhoneNumber(raw = it.phoneValue), label = it.label)
        }

      val updatedMails = contactDetail.mails?.map {
          LabeledValue(MailAddress(raw = it.mailValue), label = it.label)
      }
      val updatedWebAddress  = contactDetail.webAddress
      val updatedNote = contactDetail.note
        contactStore.execute {
            update(
                contact.mutableCopy().apply {
                    firstName = updatedFirstName
                    lastName = updatedLastName
                    phones.clear()
                    phones.addAll(updatedPhones)
                    updatedMails?.let {
                        mails.clear()
                        mails.addAll(updatedMails)
                    }
                   note = if (updatedNote == null){ null }else { Note(updatedNote) }
                    // ?: Note(updatedNote)
                    updatedWebAddress?.let {
                        webAddresses.clear()
                        webAddresses.addAll(listOf(LabeledValue(WebAddress(raw = updatedWebAddress),Label.WebsiteHomePage)))
                    }




                }
            )
        }

    }


}