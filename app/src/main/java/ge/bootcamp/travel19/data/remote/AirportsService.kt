package ge.bootcamp.travel19.data.remote

import ge.bootcamp.travel19.model.airports.Airports
import ge.bootcamp.travel19.model.airports.restrictionsbyairport.RestrictionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AirportsService {
    @GET("http://covid-restrictions-api.noxtton.com/v1/airport")
    suspend fun getAirports(
    ): Response<Airports>
}

//http://covid-restrictions-api.noxtton.com/v1/restriction/TBS/GVA

interface RestrictionByAirportService {
    @GET("http://covid-restrictions-api.noxtton.com/v1/restriction/{loc}/{dest}")
    suspend fun getRestrictionByAirport(
        @Path("loc") loc: String,
        @Path("dest") dest: String,
        @Query("nationality") nationality: String?,
        @Query("vaccine") vaccine: String?,
    ): Response<RestrictionsResponse>
}