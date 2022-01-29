package com.mtu.ceit.hhk.contactstore.domain

import com.mtu.ceit.hhk.contactstore.domain.models.ContactDetail
import javax.inject.Inject

class GetContactDetail  @Inject constructor(
    private val repos:ContactRepository
)
    :SuspendUseCase<Long,ContactDetail> {
    override suspend fun invoke(contactID: Long): ContactDetail {
      return  repos.getContactDetail(contactID)
    }
}