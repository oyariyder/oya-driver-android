package com.oyariyders.driver

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oyariyders.driver.domain.model.UserInfo
import com.oyariyders.driver.presentation.UiEvent
import com.oyariyders.driver.utils.Result
import com.oyariyders.driver.presentation.phonenumberoptions.PhoneNumberOptionsEvent
import com.oyariyders.driver.repository.DriverRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SignUpUiState(
    val otpLength: Int = 6,
    val isOtpError: Boolean = false,
    val otpValues: List<String> = List(otpLength) { "" },
    val showLoadingDialog: Boolean = false, // Add dialog states here
    val showSuccessDialog: Boolean = false,
    val errorMessage: String? = null
)

// 2. Your ViewModel
class SignUpViewModel(val repository: DriverRepository) : ViewModel() {

    private val _signUpUiState = MutableStateFlow(SignUpUiState())
    val signUpUiState: StateFlow<SignUpUiState> = _signUpUiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun updateOtpValue(index: Int, value: String) {
        val newOtpValues = _signUpUiState.value.otpValues.toMutableList()
        if (value.length <= 1 && value.all { it.isDigit() }) {
            newOtpValues[index] = value
            _signUpUiState.update { currentState ->
                currentState.copy(
                    otpValues = newOtpValues,
                    isOtpError = false
                )
            }
        }
    }

    fun isOtpInputValuesAreValid(): Boolean {
        return _signUpUiState.value.otpValues.all { it.isNotEmpty() }
    }

    fun setLoadingDialogState(state: Boolean){
        _signUpUiState.update {
            it.copy(showLoadingDialog = state)
        }
    }

    fun onEvent(event: PhoneNumberOptionsEvent) {
        when (event) {
            is PhoneNumberOptionsEvent.EnteredPhoneNum -> {
                sendOtp(event.no)
            }
        }
    }

    // 3. Handle the business logic inside the ViewModel2
    fun verifyOtp(num: String) {
        if (!isOtpInputValuesAreValid()) return

        // Launch a coroutine within the ViewModel's scope
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.Loading(true))

            // Simulate API call
            delay(2000)
            val isApiSuccess = _signUpUiState.value.otpValues.joinToString("") == "123456" // Mock logic
            val userInfo = UserInfo(
                path = num,
                otp = _signUpUiState.value.otpValues.joinToString("")
            )
            val apiResult =  repository.verifyOtp(userInfo)
            if (isApiSuccess) {
                _eventFlow.emit(UiEvent.Loading(false))
                _eventFlow.emit(UiEvent.ShowSuccess("OTP verified"))
                // Navigation or other events can be handled via a separate Channel/SharedFlow
            } else {
                _eventFlow.emit(UiEvent.Loading(false))
            }

//            when(apiResult){
//                is Result.Error -> {
//                    _eventFlow.emit(UiEvent.Loading(false))
//                    //_eventFlow.emit(UiEvent.ShowMessage(apiResult.message ?: "Error sending OTP"))
//                    _eventFlow.emit(UiEvent.ShowSuccess("OTP sent"))
//                }
//                is Result.Loading -> TODO()
//                is Result.Success -> {
//                    _eventFlow.emit(UiEvent.Loading(false))
//                    _eventFlow.emit(UiEvent.ShowSuccess("OTP sent"))
//                    Log.i(TAG, apiResult.data.toString())
//                }
//            }
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
                    _eventFlow.emit(UiEvent.ShowSuccess("OTP sent"))
                }
                is Result.Success -> {
                    _eventFlow.emit(UiEvent.Loading(false))
                    Log.i(TAG, apiResult.data.toString())
                }
            }
        }
    }
}