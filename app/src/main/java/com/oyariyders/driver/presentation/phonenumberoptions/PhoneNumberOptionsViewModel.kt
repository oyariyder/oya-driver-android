package com.oyariyders.driver.presentation.phonenumberoptions

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oyariyders.driver.presentation.UiEvent
import com.oyariyders.driver.repository.DriverRepository
import com.oyariyders.driver.utils.Result
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PhoneNumberOptionsViewModel(
    val repository: DriverRepository,
): ViewModel() {
    // State to hold the phone number
    private val _phoneNumber = MutableStateFlow<String?>(null)
    val phoneNumber = _phoneNumber.asStateFlow()

    fun onPhoneNumberChange(newNumber: String) {
        _phoneNumber.value = newNumber
    }
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun setLoadingDialogState(state: Boolean){
        _isLoading.value = state
    }

    fun onEvent(event: PhoneNumberOptionsEvent) {
        when (event) {
            is PhoneNumberOptionsEvent.EnteredPhoneNum -> {
                sendOtp(event.no)
            }
        }
    }

    private fun sendOtp(num: String) {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.Loading(true))
            val apiResult =  repository.sendOtp(num)
            when(apiResult){
                is Result.Error -> {
                    _eventFlow.emit(UiEvent.Loading(false))
                    _eventFlow.emit(UiEvent.ShowMessage(apiResult.message ?: "Error sending OTP"))
                }
                is Result.Success -> {
                    _eventFlow.emit(UiEvent.Loading(false))
                    _eventFlow.emit(UiEvent.ShowSuccess("OTP sent"))
                }
            }
        }
    }
}