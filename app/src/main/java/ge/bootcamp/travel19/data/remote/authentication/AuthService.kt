package ge.bootcamp.travel19.data.remote.authentication

import ge.bootcamp.travel19.model.auth.AuthResponse
import ge.bootcamp.travel19.model.auth.UserInfo
import retrofit2.Response
import retrofit2.http.*


interface AuthService {
    @POST("http://covid-restrictions-api.noxtton.com/v1/login")
    suspend fun logIn(@Body request: UserInfo): Response<AuthResponse>

    @POST("http://covid-restrictions-api.noxtton.com/v1/signup")
    suspend fun singUp(
        @Body request: UserInfo
    ): Response<AuthResponse>


    @GET("http://covid-restrictions-api.noxtton.com/v1_private/self")
    suspend fun getSelf(
        @Header("x-session-id") token:String?,
    ): Response<UserInfo>

}
