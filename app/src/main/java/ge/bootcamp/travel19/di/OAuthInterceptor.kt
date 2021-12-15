package ge.bootcamp.travel19.di

import android.util.Log.d
import ge.bootcamp.travel19.data.remote.OAuthService
import ge.bootcamp.travel19.data.remote.RestrictionsService
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class OAuthInterceptor @Inject constructor(
    private val refreshEndpoint: OAuthService,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(this) {
            val token = null
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
//                            tokenPreferences.edit().putString("token", t).apply()
                            d("tokeeeeen", t)
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