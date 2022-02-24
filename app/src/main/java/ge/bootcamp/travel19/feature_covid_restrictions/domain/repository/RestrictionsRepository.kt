package ge.bootcamp.travel19.feature_covid_restrictions.domain.repository

import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.RestrictionByAirport
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.restrictionsbyairport.RestrictionsResponse
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.country_restrictions.CovidRestrictions
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.Resource

interface RestrictionsRepository {
    suspend fun getRestrictionsByCountry(countryCode: String): Resource<CovidRestrictions>
    suspend fun getRestrictionByAirport(restrictionParam: RestrictionByAirport): Resource<RestrictionsResponse>
}