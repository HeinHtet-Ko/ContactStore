package com.mtu.ceit.hhk.contactstore.domain

import com.alexstyl.contactstore.Contact
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAllContacts @Inject constructor (
    private val repository: ContactRepository
):SuspendUseCase<Unit,Flow<List<Contact>>> {
    override suspend fun invoke(p: Unit): Flow<List<Contact>> {
          return  repository.getAllContacts()
    }
}