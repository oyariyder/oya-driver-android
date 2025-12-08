package com.oyariyders.driver.presentation.biodata

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oyariyders.driver.AuthPersistenceManager
import com.oyariyders.driver.domain.model.Driver
import com.oyariyders.driver.presentation.UiEvent
import com.oyariyders.driver.utils.Result
import com.oyariyders.driver.repository.DriverRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BioDataViewModel (val repository: DriverRepository, private val authPersistenceManager: AuthPersistenceManager) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _shouldContinue = MutableStateFlow(false)
    val shouldContinue = _shouldContinue.asStateFlow()

    fun toggleButtonState(state: Boolean){
        _shouldContinue.value = state
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun setLoadingDialogState(state: Boolean){
        _isLoading.value = state
    }

    suspend fun getAccessToken():String? {
        return authPersistenceManager.getAccessToken()
    }

    fun signUp(driver: Driver, token:String) {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.Loading(true))
            val apiResult =  repository.signUp(driver, token)
            when(apiResult){
                is Result.Error -> {
                    _eventFlow.emit(UiEvent.Loading(false))
                    _eventFlow.emit(UiEvent.ShowMessage(apiResult.message ?: "Error creating user account"))
                }
                is Result.Success -> {
                    _eventFlow.emit(UiEvent.Loading(false))
                    _eventFlow.emit(UiEvent.ShowSuccess(apiResult.data?.userMetadata?.firstname ?: "Created driver account"))
                }
            }
        }
    }
}