package com.oyariyders.driver

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import com.oyariyders.driver.domain.model.AuthResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthPersistenceManager(private val context: Context) {
    companion object {
        private const val PREFS_NAME = "auth_prefs_encrypted"
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
        private const val EXPIRES_AT_KEY = "expires_at"
        private const val USER_ID_KEY = "user_id"
    }

    private val encryptedPrefs: SharedPreferences by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        EncryptedSharedPreferences.create(
            PREFS_NAME,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
    /**
     * Saves the essential authentication details from the AuthResponse.
     */
    suspend fun saveAuthData(response: AuthResponse) = withContext(Dispatchers.IO) {
        encryptedPrefs.edit().apply {
            putString(ACCESS_TOKEN_KEY, response.accessToken)
            putString(REFRESH_TOKEN_KEY, response.refreshToken)
            putLong(EXPIRES_AT_KEY, response.expiresAt)
            putString(USER_ID_KEY, response.user.id)
            apply()
        }
    }

    suspend fun getAccessToken(): String? = withContext(Dispatchers.IO) {
        encryptedPrefs.getString(ACCESS_TOKEN_KEY, null)
    }
    /**
     * Retrieves the stored refresh token.
     * Returns null if no token is found.
     */
    suspend fun getRefreshToken(): String? = withContext(Dispatchers.IO) {
        encryptedPrefs.getString(REFRESH_TOKEN_KEY, null)
    }

    /**
     * Clears all stored authentication data upon logout.
     */
    suspend fun clearAuthData() = withContext(Dispatchers.IO) {
        encryptedPrefs.edit() { clear() }
    }
}