package com.mashup.damgledamgle.domain.usecase.damgle

import com.mashup.damgledamgle.domain.entity.EnglishAddress
import com.mashup.damgledamgle.domain.entity.base.Result
import com.mashup.damgledamgle.domain.repository.EnglishAddressRepository
import javax.inject.Inject

class ConvertAddressToEnglishUseCase @Inject constructor(
    private val englishAddressRepository: EnglishAddressRepository
) {
    suspend operator fun invoke(address1: String, address2: String): EnglishAddress {
        val address = "$address1 $address2"

        val result = englishAddressRepository.getEnglishAddress(address)

        return when (result) {
            is Result.Success -> EnglishAddress(result.data.sggName, result.data.roadName)
            is Result.Error -> EnglishAddress(address1, address2) // TODO: 영문 주소 조회 실패했을 겅우 처리
        }
    }
}
