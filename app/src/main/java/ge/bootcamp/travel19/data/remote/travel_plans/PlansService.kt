package ge.bootcamp.travel19.data.remote.travel_plans

import ge.bootcamp.travel19.BuildConfig
import ge.bootcamp.travel19.model.airports.plans.PostTravelPlan
import ge.bootcamp.travel19.model.airports.plans.travlePlans.GetTravelPlaneResponse
import ge.bootcamp.travel19.model.airports.plans.travlePlans.TravelPlanResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface PlansService {
    @POST("${BuildConfig.NOXTON_ENDPOINT}_private/travelplan")
    suspend fun postPlane(
        @Header("x-session-id") token:String?,
        @Body request: PostTravelPlan
    ): Response<TravelPlanResponse>


    @GET("${BuildConfig.NOXTON_ENDPOINT}_private/travelplan")
    suspend fun getTravelPlan(
        @Header("x-session-id") token:String?,
    ): Response <GetTravelPlaneResponse>

}