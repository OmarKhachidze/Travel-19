package ge.bootcamp.travel19.data.remote.countries

import javax.inject.Inject

class CountriesDataSource @Inject constructor(private val api: CountriesService) {
    suspend fun getCountry(name: String) = api.getCountries(name)
}