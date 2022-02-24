package ge.bootcamp.travel19.feature_covid_restrictions.domain.repository

import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.auth.AuthResponse
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.auth.UserInfo
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.Resource

interface AuthRepository {
    suspend fun logIn(logIn: UserInfo): Resource<AuthResponse>
    suspend fun signUp(userInfo: UserInfo): Resource<AuthResponse>
}