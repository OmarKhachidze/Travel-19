package ge.bootcamp.travel19.data.remote

import ge.bootcamp.travel19.BuildConfig
import ge.bootcamp.travel19.domain.model.airports.plans.PlanList
import ge.bootcamp.travel19.domain.model.airports.plans.SaveTravelPlan
import ge.bootcamp.travel19.domain.model.airports.plans.TravelPlanResponse
import ge.bootcamp.travel19.domain.model.token.Success
import retrofit2.Response
import retrofit2.http.*

interface TravelPlansService {
    @POST("${BuildConfig.NOXTON_ENDPOINT}_private/travelplan")
    suspend fun saveTravelPlane(
        @Header("x-session-id") token:String,
        @Body request: SaveTravelPlan
    ): Response<TravelPlanResponse>

    @GET("${BuildConfig.NOXTON_ENDPOINT}_private/travelplan")
    suspend fun getTravelPlan(
        @Header("x-session-id") token:String,
    ): Response <PlanList>

    @DELETE("${BuildConfig.NOXTON_ENDPOINT}_private/travelplan/{planId}")
    suspend fun deleteTravelPlan(
        @Path("planId") planId:String,
        @Header("x-session-id") token:String,
    ): Response <Success>

}