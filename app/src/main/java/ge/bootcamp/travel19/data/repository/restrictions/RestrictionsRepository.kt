package ge.bootcamp.travel19.data.repository.restrictions

import ge.bootcamp.travel19.domain.model.airports.RestrictionByAirport
import ge.bootcamp.travel19.domain.model.airports.restrictionsbyairport.RestrictionsResponse
import ge.bootcamp.travel19.domain.model.country_restrictions.CovidRestrictions
import ge.bootcamp.travel19.domain.states.Resource
import retrofit2.Response

interface RestrictionsRepository {
    suspend fun getRestrictionsByCountry(countryCode: String): Resource<CovidRestrictions>
    suspend fun getRestrictionByAirport(restrictionParam: RestrictionByAirport): Resource<RestrictionsResponse>
}