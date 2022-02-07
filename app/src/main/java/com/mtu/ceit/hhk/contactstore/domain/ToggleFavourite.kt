package com.mtu.ceit.hhk.contactstore.domain

import javax.inject.Inject

class ToggleFavourite @Inject constructor(
    private val repository: ContactRepository
):SuspendUseCase<Long,Unit> {
    override suspend  fun invoke(contact_id: Long) {

        repository.toggleFavourite(contact_id)

    }
}