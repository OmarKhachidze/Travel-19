package ge.bootcamp.travel19.domain.use_cases.travel_plan

import ge.bootcamp.travel19.data.repository.travel_plans.TravelPlanRepository
import ge.bootcamp.travel19.domain.model.airports.plans.PlanList
import ge.bootcamp.travel19.domain.states.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTravelPlanUseCase(private val travelPlanRepository: TravelPlanRepository) {
    operator fun invoke(token: String): Flow<Resource<PlanList>> = flow {
        emit(Resource.Loading())
        emit(travelPlanRepository.getTravelPlan(token))
    }
}