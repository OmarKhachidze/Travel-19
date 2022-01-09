package ge.bootcamp.travel19.data.remote.restrictions

import javax.inject.Inject


class RestrictionsDataSource @Inject constructor(private val api: RestrictionsService) {
    suspend fun getRestrictions(countryCode: String) = api.getCovidRestrictions(countryCode)
    suspend fun getRestByAirport(loc: String, dest: String) = api.getRestrictionByAirport(loc, dest)
    suspend fun fetchAirports() = api.getAirports()
}