package ge.bootcamp.travel19.data.repository.countries

import ge.bootcamp.travel19.domain.model.countriesv3.V3CountriesItem
import ge.bootcamp.travel19.domain.states.Resource
import retrofit2.Response

interface CountriesRepository {
    suspend fun getCountryByName(name: String): Resource<List<V3CountriesItem>>
    suspend fun getAllCountry(): Resource<List<V3CountriesItem>>
}