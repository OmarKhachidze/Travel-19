package ge.bootcamp.travel19.feature_covid_restrictions.data.remote_data_source

import ge.bootcamp.travel19.BuildConfig
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.plans.PlanList
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.plans.TravelPlanModel
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.plans.TravelPlanResponse
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.token.Success
import retrofit2.Response
import retrofit2.http.*

interface TravelPlansService {
    @POST("${BuildConfig.NOXTON_BASE_URL}_private/travelplan")
    suspend fun saveTravelPlane(
        @Header("x-session-id") token:String,
        @Body request: TravelPlanModel
    ): Response<TravelPlanResponse>

    @GET("${BuildConfig.NOXTON_BASE_URL}_private/travelplan")
    suspend fun getTravelPlan(
        @Header("x-session-id") token:String,
    ): Response <PlanList>

    @DELETE("${BuildConfig.NOXTON_BASE_URL}_private/travelplan/{planId}")
    suspend fun deleteTravelPlan(
        @Path("planId") planId:String,
        @Header("x-session-id") token:String,
    ): Response <Success>

    @PUT("${BuildConfig.NOXTON_BASE_URL}_private/travelplan/{planId}")
    suspend fun updateTravelPlan(
        @Path("planId") planId:String,
        @Body newPlan: TravelPlanModel,
        @Header("x-session-id") token:String,
    ): Response<TravelPlanResponse>

}