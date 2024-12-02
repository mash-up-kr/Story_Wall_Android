package com.mashup.damgledamgle.repository.remote

import com.mashup.damgledamgle.domain.entity.EnglishAddress
import com.mashup.damgledamgle.domain.entity.base.Result
import com.mashup.damgledamgle.domain.repository.EnglishAddressRepository
import com.mashup.damgledamgle.network.EnglishAddressServiceBuilder
import com.mashup.damgledamgle.network.EnglishAddressApi
import javax.inject.Inject

/**
 *  EnglishAddressRepositoryImpl.kt
 *
 *  Created by Minji Jeong on 2022/09/25
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class EnglishAddressRepositoryImpl @Inject constructor(
    private val serviceBuilder: EnglishAddressServiceBuilder,
) : EnglishAddressRepository {
    private val addEngApi by lazy { serviceBuilder.buildService<EnglishAddressApi>() }

    override suspend fun getEnglishAddress(address: String): Result<EnglishAddress> {
        return try {
            val resultData = addEngApi.getEnglishAddress(keyword = address)

            val sggName = resultData.results.juso[0].sggName.uppercase().replace("-", "")
            val roadName = resultData.results.juso[0].roadName.split(' ')[0].uppercase().replace("-", "")

            Result.Success(EnglishAddress(sggName, roadName))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
