package com.mashup.damgledamgle.presentation.feature.leave_story

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.damgledamgle.domain.entity.Damgle
import com.mashup.damgledamgle.domain.entity.base.launchWithResult
import com.mashup.damgledamgle.domain.usecase.damgle.ConvertAddressToEnglishUseCase
import com.mashup.damgledamgle.domain.usecase.damgle.LeaveDamgleStoryUseCase
import com.mashup.damgledamgle.domain.usecase.home.GetNaverGeocodeUseCase
import com.mashup.damgledamgle.presentation.common.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaveStoryViewModel @Inject constructor(
    private val convertAddressToEnglishUseCase: ConvertAddressToEnglishUseCase,
    private val leaveDamgleStoryRequestUseCase: LeaveDamgleStoryUseCase,
    private val getNaverGeocodeUseCase: GetNaverGeocodeUseCase,
) : ViewModel() {

    private val _leaveDamgleStoryState = MutableStateFlow<ViewState<Damgle>>(ViewState.Loading)
    val leaveDamgleStoryState: StateFlow<ViewState<Damgle>> = _leaveDamgleStoryState.asStateFlow()

    fun leaveStory(latitude: Double, longitude: Double, content: String) {
        viewModelScope.launch {
            delay(1500L)
            launchWithResult(
                result = getNaverGeocodeUseCase("$longitude,$latitude"),
                suspendOnSuccess = {
                    val address1 = it.region.area3.name
                    val address2 = it.land.name

                    val englishAddress = convertAddressToEnglishUseCase(address1, address2)

                    launchWithResult(
                        result = leaveDamgleStoryRequestUseCase(longitude, latitude, content, englishAddress.sggName, englishAddress.roadName),
                        suspendOnSuccess = { damgle -> _leaveDamgleStoryState.emit(ViewState.Success(damgle)) },
                        suspendOnError = { error -> _leaveDamgleStoryState.emit(ViewState.Error(error.toString())) }
                    )
                },
                suspendOnError = {
                    _leaveDamgleStoryState.emit(ViewState.Error(it.toString()))
                }
            )
        }
    }
}
