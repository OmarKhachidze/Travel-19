package ge.bootcamp.travel19.data.repository.travel_plans

import ge.bootcamp.travel19.domain.model.airports.plans.*
import ge.bootcamp.travel19.domain.model.token.Success
import ge.bootcamp.travel19.domain.states.Resource
import retrofit2.Response

interface TravelPlanRepository {
    suspend fun saveTravelPlan(
        token: String,
        planModel: TravelPlanModel
    ): Resource<TravelPlanResponse>

    suspend fun getTravelPlan(token: String): Resource<PlanList>
    suspend fun updateTravelPlan(
        planId: String,
        newPlan: TravelPlanModel,
        userToken: String
    ): Resource<TravelPlanResponse>

    suspend fun deleteTravelPlan(planId: String, token: String): Resource<Success>
}