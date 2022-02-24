package ge.bootcamp.travel19.feature_covid_restrictions.data.remote_data_source

import ge.bootcamp.travel19.BuildConfig
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.countriesv3.V3CountriesItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesService {

    @GET("${BuildConfig.COUNTRIES_BASE_URL}name/{name}")
    suspend fun getCountryByName(
        @Path("name") name: String
    ): Response<List<V3CountriesItem>>

    @GET("${BuildConfig.COUNTRIES_BASE_URL}all")
    suspend fun getAllCountries(): Response<List<V3CountriesItem>>

}