package com.oyariyders.driver.presentation.loginemail

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


class LoginEmailViewModel(
    val repository: DriverRepository
): ViewModel () {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun setLoadingDialogState(state: Boolean){
        _isLoading.value = state
    }

    fun sendEmailOtp(mail: String) {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.Loading(true))
            val apiResult =  repository.sendEmailOtp(mail)
            when(apiResult){
                is Result.Error -> {
                    _eventFlow.emit(UiEvent.Loading(false))
                    _eventFlow.emit(UiEvent.ShowMessage(apiResult.message ?: "Error sending OTP"))
                }
                is Result.Success -> {
                    _eventFlow.emit(UiEvent.Loading(false))
                    _eventFlow.emit(UiEvent.ShowSuccess("Email OTP sent"))
                }
            }
        }
    }
}