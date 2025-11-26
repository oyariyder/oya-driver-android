package com.oyariyders.driver.presentation.loginemail

sealed interface EmailEvent {
    data class EnteredEmail(val no: String): EmailEvent
}