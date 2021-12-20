package ge.bootcamp.travel19.data.remote

import ge.bootcamp.travel19.model.logIn.LogInResponse
import ge.bootcamp.travel19.model.logIn.LoginRequest
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


    @POST("login")
    suspend fun pushPost(
        @Body request: LoginRequest
    ): Response<LogInResponse>

}

interface VaccineService {
    @GET("vaccine")
    suspend fun getVaccine(
    ): Response<Vaccines>
}