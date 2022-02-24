package ge.bootcamp.travel19.feature_covid_restrictions.domain.repository

import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.plans.*
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.token.Success
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.Resource

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