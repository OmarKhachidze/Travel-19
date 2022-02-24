package ge.bootcamp.travel19.feature_covid_restrictions.data.remote_data_source

import ge.bootcamp.travel19.BuildConfig
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.Airports
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.auth.AuthResponse
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.nationality.Nationalities
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.vaccines.Vaccines
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {
    @GET("${BuildConfig.NOXTON_BASE_URL}/nationality")
    suspend fun getNationalities(
    ): Response<Nationalities>

    @GET("${BuildConfig.NOXTON_BASE_URL}/vaccine")
    suspend fun getVaccine(
    ): Response<Vaccines>

    @GET("${BuildConfig.NOXTON_BASE_URL}/airport")
    suspend fun getAirports(
    ): Response<Airports>

    @GET("${BuildConfig.NOXTON_BASE_URL}_private/self")
    suspend fun getUser(
        @Header("x-session-id") token:String?,
    ): Response<AuthResponse>

}
