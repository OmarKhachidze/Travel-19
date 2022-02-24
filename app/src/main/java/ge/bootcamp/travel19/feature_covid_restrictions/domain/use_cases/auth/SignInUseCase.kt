package ge.bootcamp.travel19.feature_covid_restrictions.domain.use_cases.auth

import ge.bootcamp.travel19.feature_covid_restrictions.domain.repository.AuthRepository
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.auth.AuthResponse
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.auth.UserInfo
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SignInUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(userInfo: UserInfo): Flow<Resource<AuthResponse>> = flow {
        emit(Resource.Loading())
        emit(authRepository.logIn(userInfo))
    }
}