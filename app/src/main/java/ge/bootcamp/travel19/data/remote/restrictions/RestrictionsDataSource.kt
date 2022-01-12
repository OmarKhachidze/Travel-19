package ge.bootcamp.travel19.data.remote.restrictions

import javax.inject.Inject


class RestrictionsDataSource @Inject constructor(private val api: RestrictionsService) {
    suspend fun getRestrictions(countryCode: String) = api.getCovidRestrictions(countryCode)
    suspend fun getRestByAirport(loc: String, dest: String) = api.getRestrictionByAirport(loc, dest)
    suspend fun getRestByAirportWithUserInfo(loc: String, dest: String, nationality: String, vaccine: String) = api.getRestrictionByAirportWithUserInfo(loc, dest, nationality, vaccine)
}