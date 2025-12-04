package com.oyariyders.driver.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class BioMetadata (
    @SerialName("email_verified") val emailVerified: Boolean,
    @SerialName("firstname") val firstname: String?, // Added new field
    @SerialName("phone") val phone: String?, // Added new field
    @SerialName("phone_verified") val phoneVerified: Boolean,
    @SerialName("sub") val sub: String
)