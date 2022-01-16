package com.mtu.ceit.hhk.contactstore.data

import android.content.Context
import com.alexstyl.contactstore.ContactStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides
    @Singleton
    fun provideContactStore(@ApplicationContext context: Context) =
        ContactStore.newInstance(context)

}