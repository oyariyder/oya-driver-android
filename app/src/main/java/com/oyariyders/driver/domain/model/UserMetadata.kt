package com.oyariyders.driver.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserMetadata(
    @SerialName("email_verified") val emailVerified: Boolean,
    @SerialName("phone_verified") val phoneVerified: Boolean,
    @SerialName("sub") val sub: String
)