package ge.bootcamp.travel19.feature_covid_restrictions.domain.repository

import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.countriesv3.V3CountriesItem
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.Resource

interface CountriesRepository {
    suspend fun getCountryByName(name: String): Resource<List<V3CountriesItem>>
    suspend fun getAllCountry(): Resource<List<V3CountriesItem>>
}