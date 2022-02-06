package ge.bootcamp.travel19.domain.use_cases.country

import ge.bootcamp.travel19.data.repository.countries.CountriesRepository
import ge.bootcamp.travel19.domain.model.countriesv3.V3CountriesItem
import ge.bootcamp.travel19.domain.states.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllCountryUseCase(private val countryRepository: CountriesRepository) {
    operator fun invoke(): Flow<Resource<List<V3CountriesItem>>> = flow {
        emit(countryRepository.getAllCountry())
    }
}