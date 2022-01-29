package com.mtu.ceit.hhk.contactstore.domain

interface SuspendUseCase<in Params,out T> {

    suspend fun invoke(p:Params):T

}

interface UseCase<in Params,out T> {

    fun invoke(p:Params):T

}