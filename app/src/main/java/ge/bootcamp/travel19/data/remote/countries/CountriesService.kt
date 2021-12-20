package ge.bootcamp.travel19.data.remote.countries

import ge.bootcamp.travel19.model.countries.Countries
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesService {

    @GET("name/{name}")
    suspend fun getCountries(
        @Path("name") name: String
    ): Response<List<Countries>>

}