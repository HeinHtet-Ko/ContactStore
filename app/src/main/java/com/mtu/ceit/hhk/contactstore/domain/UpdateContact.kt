package com.mtu.ceit.hhk.contactstore.domain

import com.mtu.ceit.hhk.contactstore.domain.models.ContactDetail
import javax.inject.Inject

class UpdateContact @Inject
    constructor(
       val repos:ContactRepository
    ):SuspendUseCase<ContactDetail,Unit> {
    override suspend fun invoke(contact: ContactDetail) {
         repos.updateContact(contact)
    }


}