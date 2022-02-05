package ge.bootcamp.travel19.data.repository.user_info

import ge.bootcamp.travel19.domain.model.airports.Airports
import ge.bootcamp.travel19.domain.model.auth.AuthResponse
import ge.bootcamp.travel19.domain.model.nationality.Nationalities
import ge.bootcamp.travel19.domain.model.vaccines.Vaccines
import ge.bootcamp.travel19.domain.states.Resource
import retrofit2.Response

interface UserRepository {
    suspend fun getNationalities(): Resource<Nationalities>
    suspend fun getVaccines(): Resource<Vaccines>
    suspend fun getAirports(): Resource<Airports>
    suspend fun getUser(token: String): Resource<AuthResponse>
}