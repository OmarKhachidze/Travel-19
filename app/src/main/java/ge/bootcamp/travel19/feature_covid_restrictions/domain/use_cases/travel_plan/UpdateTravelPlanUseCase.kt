package ge.bootcamp.travel19.feature_covid_restrictions.domain.use_cases.travel_plan

import ge.bootcamp.travel19.feature_covid_restrictions.domain.repository.TravelPlanRepository
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.plans.TravelPlanModel
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.plans.TravelPlanResponse
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateTravelPlanUseCase(private val travelPlanRepository: TravelPlanRepository) {
    suspend operator fun invoke(
        planId: String,
        newPlan: TravelPlanModel,
        token: String
    ): Flow<Resource<TravelPlanResponse>> = flow {
        emit(Resource.Loading())
        emit(travelPlanRepository.updateTravelPlan(planId, newPlan, token))
    }
}