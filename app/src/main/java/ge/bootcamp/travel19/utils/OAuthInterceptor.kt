package ge.bootcamp.travel19.utils

import androidx.datastore.preferences.core.stringPreferencesKey
import ge.bootcamp.travel19.feature_covid_restrictions.data.remote_data_source.OAuthService
import ge.bootcamp.travel19.feature_covid_restrictions.data.local.DataStoreManager
import ge.bootcamp.travel19.utils.Constants.BEARER
import ge.bootcamp.travel19.utils.Constants.BEARER_TOKEN_KEY
import ge.bootcamp.travel19.utils.Constants.HEADER_NAME
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
                runBlocking { dataStoreManager.readValue(stringPreferencesKey(BEARER_TOKEN_KEY)) } ?: ""

            val originalRequest = chain.request()
            val authenticationRequest =
                originalRequest.newBuilder().addHeader(HEADER_NAME, "$BEARER $token").build()
            val initialResponse = chain.proceed(authenticationRequest)

            return when (initialResponse.code) {
                401 -> {
                    val responseNewTokenLoginModel =
                        runBlocking { refreshEndpoint.getRestrictionsAccessToken() }

                    if (responseNewTokenLoginModel.isSuccessful) {
                        val newToken = responseNewTokenLoginModel.body()?.accessToken?.let { t ->
                            runBlocking {
                                dataStoreManager.storeValue(stringPreferencesKey(BEARER_TOKEN_KEY), t)
                            }
                            t
                        }
                        val newAuthenticationRequest = originalRequest.newBuilder()
                            .addHeader(HEADER_NAME, "$BEARER $newToken").build()
                        initialResponse.close()
                        chain.proceed(newAuthenticationRequest)
                    } else initialResponse
                }
                else -> initialResponse
            }
        }
    }
}