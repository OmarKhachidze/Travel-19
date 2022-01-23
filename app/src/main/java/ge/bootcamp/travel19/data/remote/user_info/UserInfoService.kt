package ge.bootcamp.travel19.data.remote.user_info

import ge.bootcamp.travel19.BuildConfig
import ge.bootcamp.travel19.model.airports.Airports
import ge.bootcamp.travel19.model.nationality.Nationalities
import ge.bootcamp.travel19.model.vaccines.Vaccines
import retrofit2.Response
import retrofit2.http.GET

interface UserInfoService {
    @GET("${BuildConfig.NOXTON_ENDPOINT}/nationality")
    suspend fun getNationalities(
    ): Response<Nationalities>

    @GET("${BuildConfig.NOXTON_ENDPOINT}/vaccine")
    suspend fun getVaccine(
    ): Response<Vaccines>

    @GET("${BuildConfig.NOXTON_ENDPOINT}/airport")
    suspend fun getAirports(
    ): Response<Airports>

}
