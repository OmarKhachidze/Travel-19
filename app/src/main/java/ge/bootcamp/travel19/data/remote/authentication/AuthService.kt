package ge.bootcamp.travel19.data.remote.authentication

import ge.bootcamp.travel19.model.logIn.LoginRequest
import ge.bootcamp.travel19.model.nationality.Nationalities
import ge.bootcamp.travel19.model.singup.SignUpResponse
import ge.bootcamp.travel19.model.singup.UserInfo
import ge.bootcamp.travel19.model.vaccines.Vaccines
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @POST("http://covid-restrictions-api.noxtton.com/v1/login")
    suspend fun logIn(@Body request: LoginRequest): Response<SignUpResponse>

    @POST("http://covid-restrictions-api.noxtton.com/v1/signup")
    suspend fun singUp(
        @Body request: UserInfo
    ): Response<SignUpResponse>
}
