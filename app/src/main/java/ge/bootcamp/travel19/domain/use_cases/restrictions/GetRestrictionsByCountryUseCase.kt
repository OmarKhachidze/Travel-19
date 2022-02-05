package ge.bootcamp.travel19.domain.use_cases.restrictions

import ge.bootcamp.travel19.data.repository.restrictions.RestrictionsRepository
import ge.bootcamp.travel19.domain.model.country_restrictions.CovidRestrictions
import ge.bootcamp.travel19.domain.states.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRestrictionsByCountryUseCase(private val restrictionsRepository: RestrictionsRepository) {
    operator fun invoke(countryCode: String): Flow<Resource<CovidRestrictions>> = flow {
        emit(Resource.Loading())
        emit(restrictionsRepository.getRestrictionsByCountry(countryCode))
    }
}