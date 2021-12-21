package ge.bootcamp.travel19.data.remote

import ge.bootcamp.travel19.model.logIn.LogInResponse
import ge.bootcamp.travel19.model.logIn.LoginRequest
import ge.bootcamp.travel19.model.nationality.Nationalities
import ge.bootcamp.travel19.model.singup.SignUpResponse
import ge.bootcamp.travel19.model.singup.UserInfo
import ge.bootcamp.travel19.model.vaccines.Vaccines
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LogInService {

    @GET("login")
    suspend fun getCustomPost(
        @Header("Content-Range") contentRange: String?
    ): Response<LoginRequest>

}

interface SignUpService {
    @POST("http://covid-restrictions-api.noxtton.com/v1/signup")
    suspend fun singUp(
        @Body request: UserInfo
    ): Response<SignUpResponse>
}

interface VaccineService {
    @GET("vaccine")
    suspend fun getVaccine(
    ): Response<Vaccines>
}

interface NationalityService {
    @GET("http://covid-restrictions-api.noxtton.com/v1/nationality")
    suspend fun getNationalities(
    ): Response<Nationalities>
}