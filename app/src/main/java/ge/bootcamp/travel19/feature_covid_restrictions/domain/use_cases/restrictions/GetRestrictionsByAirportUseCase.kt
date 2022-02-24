package ge.bootcamp.travel19.feature_covid_restrictions.domain.use_cases.restrictions

import ge.bootcamp.travel19.feature_covid_restrictions.domain.repository.RestrictionsRepository
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.RestrictionByAirport
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.restrictionsbyairport.RestrictionsResponse
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRestrictionsByAirportUseCase(private val restrictionsRepository: RestrictionsRepository) {
    operator fun invoke(restrictionParam: RestrictionByAirport): Flow<Resource<RestrictionsResponse>> =
        flow {
            emit(restrictionsRepository.getRestrictionByAirport(restrictionParam))
        }
}