package ge.bootcamp.travel19.data.remote.travel_plans

import ge.bootcamp.travel19.model.airports.plans.PostTravelPlan
import javax.inject.Inject

class TravelPlansDataSource @Inject constructor(private val api: PlansService) {
    suspend fun postTravelPlan(token: String, plan: PostTravelPlan) = api.postPlane(token, plan)
    suspend fun getTravelPlan(token: String) = api.getTravelPlan(token)
    suspend fun deleteTravelPlan(planId: String?, token: String) =
        api.deleteTravelPlan(planId, token)
}