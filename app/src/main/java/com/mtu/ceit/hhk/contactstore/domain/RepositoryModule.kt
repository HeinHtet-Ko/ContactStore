package com.mtu.ceit.hhk.contactstore.domain

import com.mtu.ceit.hhk.contactstore.data.ContactReposImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindContactRepository(contastReposImpl: ContactReposImpl):ContactRepository


}