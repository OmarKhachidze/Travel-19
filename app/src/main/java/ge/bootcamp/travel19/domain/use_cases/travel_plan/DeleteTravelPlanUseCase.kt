package ge.bootcamp.travel19.domain.use_cases.travel_plan

import ge.bootcamp.travel19.data.repository.travel_plans.TravelPlanRepository
import ge.bootcamp.travel19.domain.model.token.Success
import ge.bootcamp.travel19.domain.states.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteTravelPlanUseCase (private val travelPlanRepository: TravelPlanRepository) {
    suspend operator fun invoke(planId: String, token: String): Flow<Resource<Success>> = flow {
        emit(Resource.Loading())
        emit(travelPlanRepository.deleteTravelPlan(planId, token))
    }
}