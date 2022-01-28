package ge.bootcamp.travel19.data.remote.travel_plan

import ge.bootcamp.travel19.model.airports.plans.travlePlans.GetTravelPlaneResponse
import ge.bootcamp.travel19.model.airports.plans.travlePlans.TravelPlanResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface PlansService {
    @POST("http://covid-restrictions-api.noxtton.com/v1_private/travelplan")
    suspend fun postPlane(
        @Header("x-session-id") token:String?,
    ): Response<TravelPlanResponse>


    @GET("http://covid-restrictions-api.noxtton.com/v1_private/travelplan")
    suspend fun getTravelPlan(
        @Header("x-session-id") token:String?,
    ): Response <GetTravelPlaneResponse>

}