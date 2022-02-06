package ge.bootcamp.travel19.data.repository.authentication

import ge.bootcamp.travel19.domain.model.auth.AuthResponse
import ge.bootcamp.travel19.domain.model.auth.UserInfo
import ge.bootcamp.travel19.domain.states.Resource
import retrofit2.Response

interface AuthRepository {
    suspend fun logIn(logIn: UserInfo): Resource<AuthResponse>
    suspend fun signUp(userInfo: UserInfo): Resource<AuthResponse>
}