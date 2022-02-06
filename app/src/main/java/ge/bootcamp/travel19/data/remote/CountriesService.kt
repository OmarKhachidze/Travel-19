package ge.bootcamp.travel19.data.remote

import ge.bootcamp.travel19.BuildConfig
import ge.bootcamp.travel19.domain.model.countriesv3.V3CountriesItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesService {

    @GET("${BuildConfig.COUNTRIES_ENDPOINT}name/{name}")
    suspend fun getCountryByName(
        @Path("name") name: String
    ): Response<List<V3CountriesItem>>

    @GET("${BuildConfig.COUNTRIES_ENDPOINT}all")
    suspend fun getAllCountries(): Response<List<V3CountriesItem>>

}