package com.oyariyders.driver.presentation.phonenumberoptions

sealed interface PhoneNumberOptionsEvent {
    data class EnteredPhoneNum(val no: String): PhoneNumberOptionsEvent
}