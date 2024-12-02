package com.mashup.damgledamgle.domain.repository

import com.mashup.damgledamgle.domain.entity.EnglishAddress
import com.mashup.damgledamgle.domain.entity.base.Result

/**
 *  EnglishAddressRepository.kt
 *
 *  Created by Minji Jeong on 2022/09/25
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

interface EnglishAddressRepository {
 suspend fun getEnglishAddress(address: String): Result<EnglishAddress>
}
