package ge.bootcamp.travel19.feature_covid_restrictions.domain.use_cases.user

import ge.bootcamp.travel19.feature_covid_restrictions.domain.repository.UserRepository
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.auth.AuthResponse
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(token: String): Flow<Resource<AuthResponse>> = flow {
        emit(userRepository.getUser(token))
    }
}