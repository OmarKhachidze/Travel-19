package ge.bootcamp.travel19.utils

import androidx.datastore.preferences.core.stringPreferencesKey
import ge.bootcamp.travel19.data.remote.restrictions.OAuthService
import ge.bootcamp.travel19.datastore.DataStoreManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class OAuthInterceptor @Inject constructor(
    private val refreshEndpoint: OAuthService,
    private val dataStoreManager: DataStoreManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(this) {
            val token =
                runBlocking { dataStoreManager.readValue(stringPreferencesKey("BearerToken")) } ?: ""

            val originalRequest = chain.request()
            val authenticationRequest =
                originalRequest.newBuilder().addHeader("Authorization", "Bearer $token").build()
            val initialResponse = chain.proceed(authenticationRequest)

            return when (initialResponse.code) {
                401 -> {
                    val responseNewTokenLoginModel =
                        runBlocking { refreshEndpoint.getRestrictionsAccessToken() }

                    if (responseNewTokenLoginModel.isSuccessful) {
                        val newToken = responseNewTokenLoginModel.body()?.accessToken?.let { t ->
                            runBlocking {
                                dataStoreManager.storeValue(stringPreferencesKey("BearerToken"), t)
                            }
                            t
                        }
                        val newAuthenticationRequest = originalRequest.newBuilder()
                            .addHeader("Authorization", "Bearer $newToken").build()
                        initialResponse.close()
                        chain.proceed(newAuthenticationRequest)
                    } else initialResponse
                }
                else -> initialResponse
            }
        }
    }
}