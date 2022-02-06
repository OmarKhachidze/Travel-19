package ge.bootcamp.travel19.data.remote

import ge.bootcamp.travel19.BuildConfig
import ge.bootcamp.travel19.domain.model.airports.Airports
import ge.bootcamp.travel19.domain.model.auth.AuthResponse
import ge.bootcamp.travel19.domain.model.nationality.Nationalities
import ge.bootcamp.travel19.domain.model.vaccines.Vaccines
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {
    @GET("${BuildConfig.NOXTON_ENDPOINT}/nationality")
    suspend fun getNationalities(
    ): Response<Nationalities>

    @GET("${BuildConfig.NOXTON_ENDPOINT}/vaccine")
    suspend fun getVaccine(
    ): Response<Vaccines>

    @GET("${BuildConfig.NOXTON_ENDPOINT}/airport")
    suspend fun getAirports(
    ): Response<Airports>

    @GET("${BuildConfig.NOXTON_ENDPOINT}_private/self")
    suspend fun getUser(
        @Header("x-session-id") token:String?,
    ): Response<AuthResponse>

}
