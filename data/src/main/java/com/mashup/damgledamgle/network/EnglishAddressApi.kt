package com.mashup.damgledamgle.network

import com.mashup.damgledamgle.repository.spec.response.EnglishAddressResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *  EnglishAddressApi.kt
 *
 *  Created by Minji Jeong on 2022/09/25
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

interface EnglishAddressApi {

 /**
  * 영어주소 찾기
  */
 @GET("/addrlink/addrEngApi.do")
 suspend fun getEnglishAddress(
  @Query("keyword") keyword: String,
  @Query("confmKey") confmKey: String = "devU01TX0FVVEgyMDIyMTEwODIzMzMxMzExMzIwMzk=",
  @Query("resultType") resultType: String = "json"
 ): EnglishAddressResponse
}
