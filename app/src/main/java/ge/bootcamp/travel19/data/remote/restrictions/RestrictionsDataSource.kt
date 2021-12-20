package ge.bootcamp.travel19.data.remote.restrictions

import javax.inject.Inject


class RestrictionsDataSource @Inject constructor(private val api: RestrictionsService) {
    suspend fun getRestrictions(countryCode: String) = api.getCovidRestrictions(countryCode)
}