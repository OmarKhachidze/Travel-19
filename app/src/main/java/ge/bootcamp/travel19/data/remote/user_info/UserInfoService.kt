package ge.bootcamp.travel19.data.remote.user_info

import ge.bootcamp.travel19.model.airports.Airports
import ge.bootcamp.travel19.model.airports.restrictionsbyairport.RestrictionsResponse
import ge.bootcamp.travel19.model.nationality.Nationalities
import ge.bootcamp.travel19.model.vaccines.Vaccines
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserInfoService {
    @GET("http://covid-restrictions-api.noxtton.com/v1/nationality")
    suspend fun getNationalities(
    ): Response<Nationalities>

    @GET("http://covid-restrictions-api.noxtton.com/v1/vaccine")
    suspend fun getVaccine(
    ): Response<Vaccines>

    @GET("http://covid-restrictions-api.noxtton.com/v1/airport")
    suspend fun getAirports(
    ): Response<Airports>

}
