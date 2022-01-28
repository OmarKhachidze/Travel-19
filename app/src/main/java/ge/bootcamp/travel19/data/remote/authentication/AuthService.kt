package ge.bootcamp.travel19.data.remote.authentication

import ge.bootcamp.travel19.BuildConfig
import ge.bootcamp.travel19.model.auth.AuthResponse
import ge.bootcamp.travel19.model.auth.UserInfo
import retrofit2.Response
import retrofit2.http.*


interface AuthService {
    @POST("${BuildConfig.NOXTON_ENDPOINT}/login")
    suspend fun logIn(@Body request: UserInfo): Response<AuthResponse>

    @POST("${BuildConfig.NOXTON_ENDPOINT}/signup")
    suspend fun singUp(
        @Body request: UserInfo
    ): Response<AuthResponse>


    @GET("${BuildConfig.NOXTON_ENDPOINT}_private/self")
    suspend fun getSelf(
        @Header("x-session-id") token:String?,
    ): Response<AuthResponse>

}
