package ge.bootcamp.travel19.data.remote

import ge.bootcamp.travel19.BuildConfig
import ge.bootcamp.travel19.domain.model.auth.AuthResponse
import ge.bootcamp.travel19.domain.model.auth.UserInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthService {
    @POST("${BuildConfig.NOXTON_ENDPOINT}/login")
    suspend fun logIn(@Body request: UserInfo): Response<AuthResponse>

    @POST("${BuildConfig.NOXTON_ENDPOINT}/signup")
    suspend fun singUp(
        @Body request: UserInfo
    ): Response<AuthResponse>
}
