package com.oyariyders.driver.domain.model
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonElement

@Serializable
data class BioResponse (
    @SerialName("id") val id: String,
    @SerialName("aud") val aud: String,
    @SerialName("role") val role: String,
    @SerialName("email") val email: String?, // Nullable since it's an empty string
    @SerialName("phone") val phone: String,
    @SerialName("phone_confirmed_at") val phoneConfirmedAt: String?,
    @SerialName("confirmed_at") val confirmedAt: String?,
    @SerialName("last_sign_in_at") val lastSignInAt: String?,
    @SerialName("app_metadata") val appMetadata: AppMetadata,
    @SerialName("user_metadata") val userMetadata: BioMetadata,
    @SerialName("identities") val identities: List<Identity>,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("is_anonymous") val isAnonymous: Boolean
)