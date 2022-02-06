package ge.bootcamp.travel19.data.remote

import ge.bootcamp.travel19.BuildConfig
import ge.bootcamp.travel19.domain.model.airports.restrictionsbyairport.RestrictionsResponse
import ge.bootcamp.travel19.domain.model.country_restrictions.CovidRestrictions
import retrofit2.Response
import retrofit2.http.*

interface RestrictionsService {
    @GET("duty-of-care/diseases/covid19-area-report")
    suspend fun getCovidRestrictionsByCountry(
            @Query("countryCode") countryCode: String
    ): Response<CovidRestrictions>

    @GET("${BuildConfig.NOXTON_ENDPOINT}/restriction/{loc}/{dest}")
    suspend fun getRestrictionByAirport(
            @Path("loc") loc: String,
            @Path("dest") dest: String,
            @Query("nationality") nationality: String?,
            @Query("vaccine") vaccine: String?,
            @Query("transfer") transfer: String?,
    ): Response<RestrictionsResponse>

}
