package ge.bootcamp.travel19.domain.use_cases.country

import android.text.Editable
import ge.bootcamp.travel19.data.repository.countries.CountriesRepository
import ge.bootcamp.travel19.domain.model.countriesv3.V3CountriesItem
import ge.bootcamp.travel19.domain.states.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCountryByNameUseCase(private val countryRepository: CountriesRepository) {
    operator fun invoke(text: Editable?): Flow<Resource<List<V3CountriesItem>>> = flow {
        text?.let { name ->
            if (name.isNotEmpty()) {
                emit(Resource.Loading())
                emit(countryRepository.getCountryByName(name.toString()))
            } else
                emit(Resource.Empty())
        }
    }
}