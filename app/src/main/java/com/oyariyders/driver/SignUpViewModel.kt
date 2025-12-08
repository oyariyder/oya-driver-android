package com.oyariyders.driver

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
class SignUpViewModel(val repository: DriverRepository, private val authPersistenceManager: AuthPersistenceManager) : ViewModel() {

    private val _signUpUiState = MutableStateFlow(SignUpUiState())
    val signUpUiState: StateFlow<SignUpUiState> = _signUpUiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    //State to hold accessToken
    private val _accessToken = MutableStateFlow("")
    val accessToken = _accessToken.asStateFlow()

    val otpOptions = listOf<String>(
        "whatsapp", "sms"
    )

    private val _selectedOption = mutableStateOf(otpOptions[1]) // Starts as "whatsapp"
    val selectedOption: State<String> = _selectedOption

    fun toggleSelectedOption(){
        val currentOption = _selectedOption.value
        // Find the index of the current option
        val currentIndex = otpOptions.indexOf(currentOption)
        // Determine the next index (0 -> 1, 1 -> 0). Use modulo for clean toggling.
        val nextIndex = (currentIndex + 1) % otpOptions.size
        // Assign the new String value directly to the MutableState value property
        _selectedOption.value = otpOptions[nextIndex]
    }

    fun saveAccessToken(token: String){
        _accessToken.value = token
    }

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
                resendOtp(event.no)
            }
        }
    }

    // 3. Handle the business logic inside the ViewModel2
    fun verifyOtp(num: String) {
        if (!isOtpInputValuesAreValid()) return

        // Launch a coroutine within the ViewModel's scope
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.Loading(true))
            //set the current selected option
            val currentSelectedOption = _selectedOption.value
            // Simulate API call
            //delay(2000)
            val isApiSuccess = _signUpUiState.value.otpValues.joinToString("") == "123456" // Mock logic
            val userInfo = UserInfo(
                phone = num,
                token = _signUpUiState.value.otpValues.joinToString(""),
                type = currentSelectedOption
            )
            val apiResult =  repository.verifyOtp(userInfo)
//            if (isApiSuccess) {
//                _eventFlow.emit(UiEvent.Loading(false))
//                _eventFlow.emit(UiEvent.ShowSuccess("OTP verified"))
//                // Navigation or other events can be handled via a separate Channel/SharedFlow
//            } else {
//                _eventFlow.emit(UiEvent.Loading(false))
//            }

            when(apiResult){
                is Result.Error -> {
                    _eventFlow.emit(UiEvent.Loading(false))
                    _eventFlow.emit(UiEvent.ShowMessage(apiResult.message ?: "Error verifying OTP"))
                }
                is Result.Success -> {
                    _eventFlow.emit(UiEvent.Loading(false))
                    // IMPORTANT: Safely access the AuthResponse and save it
                    val authResponse = apiResult.data
                    if (authResponse != null){
                        // Call the suspend function to save the data securely
                        authPersistenceManager.saveAuthData(authResponse)
                    }
                    _eventFlow.emit(UiEvent.ShowSuccess(apiResult.data?.accessToken.toString()))
                }
            }
        }
    }

    fun resendOtp(num: String) {
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
                    _eventFlow.emit(UiEvent.ShowMessage("OTP sent"))
                }
            }
        }
    }

    fun resendEmailOtp(mail: String) {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.Loading(true))
            val apiResult =  repository.sendEmailOtp(mail)
            when(apiResult){
                is Result.Error -> {
                    _eventFlow.emit(UiEvent.Loading(false))
                    _eventFlow.emit(UiEvent.ShowMessage(apiResult.message ?: "Error sending email OTP"))
                }
                is Result.Success -> {
                    _eventFlow.emit(UiEvent.Loading(false))
                    _eventFlow.emit(UiEvent.ShowMessage("Email OTP sent"))
                }
            }
        }
    }
}