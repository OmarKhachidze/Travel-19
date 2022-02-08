package ge.bootcamp.travel19.domain.use_cases.travel_plan

import ge.bootcamp.travel19.data.repository.travel_plans.TravelPlanRepository
import ge.bootcamp.travel19.domain.model.airports.plans.TravelPlanModel
import ge.bootcamp.travel19.domain.model.airports.plans.TravelPlanResponse
import ge.bootcamp.travel19.domain.states.Resource
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