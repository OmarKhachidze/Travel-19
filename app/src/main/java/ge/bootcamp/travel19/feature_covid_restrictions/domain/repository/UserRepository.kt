package ge.bootcamp.travel19.feature_covid_restrictions.domain.repository

import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.Airports
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.auth.AuthResponse
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.nationality.Nationalities
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.vaccines.Vaccines
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.Resource

interface UserRepository {
    suspend fun getNationalities(): Resource<Nationalities>
    suspend fun getVaccines(): Resource<Vaccines>
    suspend fun getAirports(): Resource<Airports>
    suspend fun getUser(token: String): Resource<AuthResponse>
}