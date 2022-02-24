package ge.bootcamp.travel19.feature_covid_restrictions.domain.use_cases.restrictions

import ge.bootcamp.travel19.feature_covid_restrictions.domain.repository.RestrictionsRepository
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.country_restrictions.CovidRestrictions
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRestrictionsByCountryUseCase(private val restrictionsRepository: RestrictionsRepository) {
    operator fun invoke(countryCode: String): Flow<Resource<CovidRestrictions>> = flow {
        emit(restrictionsRepository.getRestrictionsByCountry(countryCode))
    }
}