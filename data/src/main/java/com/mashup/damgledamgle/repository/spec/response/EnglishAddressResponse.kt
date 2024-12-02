package com.mashup.damgledamgle.repository.spec.response

import com.squareup.moshi.Json

/**
 *  EnglishAddressResponse.kt
 *
 *  Created by Minji Jeong on 2022/09/25
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

data class EnglishAddressResponse(
    @Json(name = "results") val results: EnglishAddressList
) {
    data class EnglishAddressList(
        @Json(name = "juso") val juso: List<EnglishAddress>
    )

    data class EnglishAddress(
        @Json(name = "korAddr") val korAddr: String,  // 한글 도로명주소
        @Json(name = "sggNm") val sggName: String,    // 영문 시군구명
        @Json(name = "rn") val roadName: String,      // 영문 도로명
    )
}
