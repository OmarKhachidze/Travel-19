package ge.bootcamp.travel19.domain.use_cases.restrictions

import ge.bootcamp.travel19.data.repository.restrictions.RestrictionsRepository
import ge.bootcamp.travel19.domain.model.airports.RestrictionByAirport
import ge.bootcamp.travel19.domain.model.airports.restrictionsbyairport.RestrictionsResponse
import ge.bootcamp.travel19.domain.states.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRestrictionsByAirportUseCase(private val restrictionsRepository: RestrictionsRepository) {
    operator fun invoke(restrictionParam: RestrictionByAirport): Flow<Resource<RestrictionsResponse>> =
        flow {
            emit(restrictionsRepository.getRestrictionByAirport(restrictionParam))
        }
}