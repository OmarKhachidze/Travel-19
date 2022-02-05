package ge.bootcamp.travel19.data.repository.travel_plans

import ge.bootcamp.travel19.domain.model.airports.plans.*
import ge.bootcamp.travel19.domain.model.token.Success
import ge.bootcamp.travel19.domain.states.Resource

interface TravelPlanRepository {
    suspend fun saveTravelPlan(
        token: String,
        plan: SaveTravelPlan
    ): Resource<TravelPlanResponse>

    suspend fun getTravelPlan(token: String): Resource<PlanList>
    suspend fun deleteTravelPlan(planId: String, token: String): Resource<Success>
}