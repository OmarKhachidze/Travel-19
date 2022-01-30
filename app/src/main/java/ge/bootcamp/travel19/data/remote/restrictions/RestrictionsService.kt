package ge.bootcamp.travel19.data.remote.restrictions

import ge.bootcamp.travel19.BuildConfig
import ge.bootcamp.travel19.model.airports.restrictionsbyairport.RestrictionsResponse
import ge.bootcamp.travel19.model.country_restrictions.CovidRestrictions
import ge.bootcamp.travel19.model.token.Token
import ge.bootcamp.travel19.utils.Keys
import retrofit2.Response
import retrofit2.http.*

interface RestrictionsService {
    @GET("duty-of-care/diseases/covid19-area-report")
    suspend fun getCovidRestrictions(
            @Query("countryCode") countryCode: String
    ): Response<CovidRestrictions>

    @GET("https://run.mocky.io/v3/085d6e07-3a99-41ac-8744-7c40bb70be31")
    suspend fun getCovidRestrictionsTest(): Response<CovidRestrictions>

    @GET("${BuildConfig.NOXTON_ENDPOINT}/restriction/{loc}/{dest}")
    suspend fun getRestrictionByAirport(
            @Path("loc") loc: String,
            @Path("dest") dest: String,
    ): Response<RestrictionsResponse>

    @GET("${BuildConfig.NOXTON_ENDPOINT}/restriction/{loc}/{dest}")
    suspend fun getRestrictionByAirportWithUserInfo(
            @Path("loc") loc: String,
            @Path("dest") dest: String,
            @Query("nationality") nationality: String,
            @Query("vaccine") vaccine: String,
    ): Response<RestrictionsResponse>

}

interface OAuthService {
    @FormUrlEncoded
    @POST("${BuildConfig.AMADEUS_ENDPOINT}security/oauth2/token")
    suspend fun getRestrictionsAccessToken(
        @Field("grant_type") type: String = "client_credentials",
        @Field("client_id") key: String = Keys.clientId(),
        @Field("client_secret") secret: String = Keys.clientSecret()
    ): Response<Token>
}
