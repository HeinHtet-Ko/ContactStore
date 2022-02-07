package com.mtu.ceit.hhk.contactstore.domain

import android.util.Log
import javax.inject.Inject

class DeleteContact @Inject constructor(
    private val repository: ContactRepository
):SuspendUseCase<List<Long>,Unit> {
    override suspend fun invoke(contactIdList: List<Long>) {

        repository.deleteContact(contactIdList)
    }
}