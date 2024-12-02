package com.mashup.damgledamgle.di

import com.mashup.damgledamgle.domain.repository.*
import com.mashup.damgledamgle.repository.datastore.repository.DataStoreRepositoryImpl
import com.mashup.damgledamgle.repository.remote.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *  RepositoryModule.kt
 *
 *  Created by Minji Jeong on 2022/07/04
 *  Copyright © 2022 MashUp All rights reserved.
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindDataStoreRepository(impl: DataStoreRepositoryImpl): DataStoreRepository

    @Singleton
    @Binds
    abstract fun bindOnboardingRepository(impl: OnboardingRepositoryImpl): OnboardingRepository

    @Singleton
    @Binds
    abstract fun bindMapRepository(impl: MapRepositoryImpl): MapRepository

    @Singleton
    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindDamgleRepository(impl: DamgleRepositoryImpl): DamgleRepository

    @Singleton
    @Binds
    abstract fun bindEnglishAddressRepository(impl: EnglishAddressRepositoryImpl): EnglishAddressRepository
}
