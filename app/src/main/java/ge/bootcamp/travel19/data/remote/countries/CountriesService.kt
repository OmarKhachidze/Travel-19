package ge.bootcamp.travel19.data.remote.countries

import ge.bootcamp.travel19.BuildConfig
import ge.bootcamp.travel19.model.countries.Countries
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesService {

    @GET("${BuildConfig.COUNTRIES_ENDPOINT}{name}")
    suspend fun getCountries(
        @Path("name") name: String
    ): Response<List<Countries>>

    @GET("${BuildConfig.COUNTRIES_ENDPOINT}all")
    suspend fun getAllCountries(): Response<List<Countries>>

}