package com.mashup.damgledamgle.presentation.feature.home

import android.graphics.Bitmap
import android.icu.util.Calendar
import android.os.CountDownTimer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.damgledamgle.R


class HomeViewModel : ViewModel() {

    private var countDownTimer: CountDownTimer? = null

    var totalDiffTime = 0L
    private val _time = MutableLiveData("")
    val time: LiveData<String> = _time


    private val _iconRes = mutableStateOf(R.drawable.ic_angry_small)
    val iconsRes: State<Int> = _iconRes

    fun changeMarkerValue(resId: Int) {
        _iconRes.value = resId
    }

    private var _onBitmapCreated = MutableLiveData<Bitmap?>(null)
    var onBitmapGenerated: LiveData<Bitmap?> = _onBitmapCreated

    fun bitmapCreated(bitmap: Bitmap?) {
        _onBitmapCreated.value = bitmap
    }

    //서버에서 받는 마커 표시 위치 ArrayList<Info>
    //Info는 아이콘, 읽음 처리, 위도 경도에 대한 정보를 담은 data class
    fun getMakerList(): ArrayList<MakerInfo> {
        val list: ArrayList<MakerInfo> = arrayListOf()

        list.add(MakerInfo(R.drawable.ic_heart_small, false, 37.5455113, 127.0657011))
        list.add(MakerInfo(R.drawable.ic_angry_small, true, 37.5448118, 127.0649041))
        list.add(MakerInfo(R.drawable.ic_amazing_small, false, 37.54529, 127.06630))

        return list
    }

    fun getCalendarLastDay(): String {
        var result = ""
        val calendar = Calendar.getInstance()
        val lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) //마지막 날짜
        val today = calendar.get(Calendar.DATE).toString().toInt()
        val diff = lastDay - today
        result = "D-$diff"

        if (diff < 1) {
            result = getCalDiffTime(calendar)

        }
        return result
    }

    private fun getCalDiffTime(calendar: Calendar): String {
        val nowTime = calendar.time

        val eventDate = Calendar.getInstance()
        eventDate[Calendar.YEAR] = calendar.get(Calendar.YEAR)
        eventDate[Calendar.MONTH] = calendar.get(Calendar.MONTH)
        eventDate[Calendar.DAY_OF_MONTH] = 25
        eventDate[Calendar.HOUR_OF_DAY] = 0
        eventDate[Calendar.MINUTE] = 0
        eventDate[Calendar.SECOND] = 0

        totalDiffTime = eventDate.timeInMillis - nowTime.time

        val hours = totalDiffTime / (60 * 60 * 1000)
        val minutes = totalDiffTime / (1000 * 60) % 60
        val seconds = (totalDiffTime / 1000) % 60

        return "$hours:$minutes:$seconds"
    }

    fun startTimer() {
        countDownTimer = object : CountDownTimer(totalDiffTime, 1000) {
            override fun onTick(millisRemaining: Long) {
                val hms = TimeUtil.formatTime(millisRemaining)
                _time.value = hms
            }
            override fun onFinish() {
                pauseTimer()
            }
        }.start()

    }

    private fun pauseTimer() {
        countDownTimer?.cancel()
    }


}

data class MakerInfo(
    val resId: Int,
    val isRead: Boolean,
    val latitude: Double,
    val longitude: Double
)