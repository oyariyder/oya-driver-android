package com.oyariyders.driver.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthUser(
    @SerialName("id") val id: String,
    @SerialName("aud") val aud: String,
    @SerialName("role") val role: String,
    @SerialName("email") val email: String?, // Made nullable as it was an empty string in the example
    @SerialName("phone") val phone: String,
    @SerialName("phone_confirmed_at") val phoneConfirmedAt: String?,
    @SerialName("confirmation_sent_at") val confirmationSentAt: String,
    @SerialName("confirmed_at") val confirmedAt: String?,
    @SerialName("last_sign_in_at") val lastSignInAt: String?,
    @SerialName("app_metadata") val appMetadata: AppMetadata,
    @SerialName("user_metadata") val userMetadata: UserMetadata,
    @SerialName("identities") val identities: List<Identity>,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("is_anonymous") val isAnonymous: Boolean
)