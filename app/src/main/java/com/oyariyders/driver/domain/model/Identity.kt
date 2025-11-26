package com.oyariyders.driver.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Identity(
    @SerialName("identity_id") val identityId: String,
    @SerialName("id") val id: String,
    @SerialName("user_id") val userId: String,
    // Use Map<String, JsonElement> or Map<String, *>* if you need to access specific dynamic types,
    // but Map<String, Any?> is often sufficient for basic parsing.
    @SerialName("identity_data") val identityData: Map<String, JsonElement>? = null,
    @SerialName("provider") val provider: String,
    @SerialName("last_sign_in_at") val lastSignInAt: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String
)