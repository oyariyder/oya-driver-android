package com.oyariyders.driver.presentation.loginphone

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginPhoneViewModel: ViewModel() {
    private val _phoneNumber = MutableStateFlow<String?>(null)
    val phoneNumber = _phoneNumber.asStateFlow()

    private val _shouldContinue = MutableStateFlow(false)
    val shouldContinue = _shouldContinue.asStateFlow()

    fun toggleButtonState(state: Boolean){
        _shouldContinue.value = state
    }

    fun onPhoneNumberChange(newNumber: String) {
        _phoneNumber.value = newNumber
    }
}