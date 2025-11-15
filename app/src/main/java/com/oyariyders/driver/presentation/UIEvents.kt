package com.oyariyders.driver.presentation

sealed interface UiEvent {
    data class ShowMessage(val message: String) : UiEvent
    data class Loading(val boolean: Boolean) : UiEvent
    data class ShowSuccess(val message: String) : UiEvent
}