package com.oyariyders.driver.presentation.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oyariyders.driver.presentation.UiEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OTPScreenViewModel(
    //val repository: RiderRepository
): ViewModel() {
    private val initialTimeSeconds = 60L
    // Timer state to display
    private val _secondsRemaining = MutableStateFlow(initialTimeSeconds)
    val secondsRemaining = _secondsRemaining.asStateFlow()
    private val _smsSecondsRemaining = MutableStateFlow(initialTimeSeconds)
    val smsSecondsRemaining = _smsSecondsRemaining.asStateFlow()

    // State to control button enablement
    private val _isTimerRunning = MutableStateFlow(true)
    val isTimerRunning = _isTimerRunning.asStateFlow()

    private val _isSmsTimerRunning = MutableStateFlow(true)
    val isSmsTimerRunning = _isSmsTimerRunning.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        startTimer()
        startSmsTimer()
    }

    fun startTimer() {
        if (!_isTimerRunning.value) {
            // Reset state if timer is starting again
            _secondsRemaining.value = initialTimeSeconds
            _isTimerRunning.value = true
        }

        viewModelScope.launch {
            // Flow will stop automatically when ViewModel is cleared
            while (_secondsRemaining.value > 0) {
                delay(1000L) // Wait for 1 second
                _secondsRemaining.value--
            }
            // Timer finished, enable the button
            _isTimerRunning.value = false
        }
    }

    fun startSmsTimer(){
        if (!_isSmsTimerRunning.value) {
            // Reset state if timer is starting again
            _smsSecondsRemaining.value = initialTimeSeconds
            _isSmsTimerRunning.value = true
        }

        viewModelScope.launch {
            // Flow will stop automatically when ViewModel is cleared
            while (_smsSecondsRemaining.value > 0) {
                delay(1000L) // Wait for 1 second
                _smsSecondsRemaining.value--
            }
            _isSmsTimerRunning.value = false
        }
    }

//    fun onEvent(event: PhoneNumberOptionsEvent) {
//        when (event) {
//            is PhoneNumberOptionsEvent.EnteredPhoneNum -> {
//                sendOtp(event.no)
//            }
//        }
//    }

//    private fun sendOtp(num: String) {
//        viewModelScope.launch {
//            val apiResult =  repository.sendOtp(num)
//            when(apiResult){
//                is Result.Error -> _eventFlow.emit(UiEvent.ShowMessage("Error sending OTP"))
//                is Result.Loading -> TODO()
//                is Result.Success -> {
//                    println(apiResult.data)
//                }
//            }
//        }
//    }
}