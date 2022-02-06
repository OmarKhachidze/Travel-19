package ge.bootcamp.travel19.domain.use_cases.auth

import ge.bootcamp.travel19.data.repository.authentication.AuthRepository
import ge.bootcamp.travel19.domain.model.auth.AuthResponse
import ge.bootcamp.travel19.domain.model.auth.UserInfo
import ge.bootcamp.travel19.domain.states.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SignUpUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(userInfo: UserInfo): Flow<Resource<AuthResponse>> = flow {
        emit(Resource.Loading())
        emit(authRepository.signUp(userInfo))
    }
}