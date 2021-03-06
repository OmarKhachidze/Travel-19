package ge.bootcamp.travel19.feature_covid_restrictions.data.remote_data_source

import ge.bootcamp.travel19.BuildConfig
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.restrictionsbyairport.RestrictionsResponse
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.country_restrictions.CovidRestrictions
import retrofit2.Response
import retrofit2.http.*

interface RestrictionsService {
    @GET("duty-of-care/diseases/covid19-area-report")
    suspend fun getCovidRestrictionsByCountry(
            @Query("countryCode") countryCode: String
    ): Response<CovidRestrictions>

    @GET("${BuildConfig.NOXTON_BASE_URL}/restriction/{loc}/{dest}")
    suspend fun getRestrictionByAirport(
            @Path("loc") loc: String,
            @Path("dest") dest: String,
            @Query("nationality") nationality: String?,
            @Query("vaccine") vaccine: String?,
            @Query("transfer") transfer: String?,
    ): Response<RestrictionsResponse>

}
