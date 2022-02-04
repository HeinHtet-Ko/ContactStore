package com.mtu.ceit.hhk.contactstore.domain

import com.mtu.ceit.hhk.contactstore.domain.models.ContactDetail
import javax.inject.Inject

class PutContact @Inject constructor(
    val repository: ContactRepository
):SuspendUseCase<ContactDetail,Unit> {
    override suspend fun invoke(p: ContactDetail) {
        repository.insertContact(p)
    }


}