package ge.bootcamp.travel19.data.remote.restrictions

import ge.bootcamp.travel19.model.airports.Airports
import ge.bootcamp.travel19.model.airports.restrictionsbyairport.RestrictionsResponse
import ge.bootcamp.travel19.model.restrictions.CovidRestrictions
import ge.bootcamp.travel19.model.token.Token
import retrofit2.Response
import retrofit2.http.*

interface RestrictionsService {
    @GET("duty-of-care/diseases/covid19-area-report")
    suspend fun getCovidRestrictions(
        @Query("countryCode") countryCode: String
    ): Response<CovidRestrictions>

    @GET("http://covid-restrictions-api.noxtton.com/v1/restriction/{loc}/{dest}")
    suspend fun getRestrictionByAirport(
        @Path("loc") loc: String,
        @Path("dest") dest: String,
    ): Response<RestrictionsResponse>

    @GET("http://covid-restrictions-api.noxtton.com/v1/airport")
    suspend fun getAirports(
    ): Response<Airports>

}

interface OAuthService {
    @FormUrlEncoded
    @POST("https://test.api.amadeus.com/v1/security/oauth2/token")
    suspend fun getRestrictionsAccessToken(
        @Field("grant_type") type: String = "client_credentials",
        @Field("client_id") key: String = "TubAweA8GMHVhQrPSoF0gsuI3fJKUOZQ",
        @Field("client_secret") secret: String = "jARPojAkUiEBGA05"
    ): Response<Token>
}
