package ge.bootcamp.travel19.feature_covid_restrictions.domain.use_cases.travel_plan

import ge.bootcamp.travel19.feature_covid_restrictions.domain.repository.TravelPlanRepository
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.token.Success
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteTravelPlanUseCase (private val travelPlanRepository: TravelPlanRepository) {
    suspend operator fun invoke(planId: String, token: String): Flow<Resource<Success>> = flow {
        emit(Resource.Loading())
        emit(travelPlanRepository.deleteTravelPlan(planId, token))
    }
}