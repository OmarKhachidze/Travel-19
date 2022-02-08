package ge.bootcamp.travel19.domain.use_cases.travel_plan

import ge.bootcamp.travel19.data.repository.travel_plans.TravelPlanRepository
import ge.bootcamp.travel19.domain.model.airports.plans.TravelPlanModel
import ge.bootcamp.travel19.domain.model.airports.plans.TravelPlanResponse
import ge.bootcamp.travel19.domain.states.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveTravelPlanUseCase(private val travelPlanRepository: TravelPlanRepository) {
    suspend operator fun invoke(
        token: String,
        travelPlanModel: TravelPlanModel
    ): Flow<Resource<TravelPlanResponse>> = flow {
        emit(Resource.Loading())
        emit(travelPlanRepository.saveTravelPlan(token, travelPlanModel))
    }
}