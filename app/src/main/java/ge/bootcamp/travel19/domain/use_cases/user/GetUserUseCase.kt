package ge.bootcamp.travel19.domain.use_cases.user

import ge.bootcamp.travel19.data.repository.user_info.UserRepository
import ge.bootcamp.travel19.domain.model.airports.Airports
import ge.bootcamp.travel19.domain.model.auth.AuthResponse
import ge.bootcamp.travel19.domain.states.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class GetUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(token: String): Flow<Resource<AuthResponse>> = flow {
        emit(userRepository.getUser(token))
    }
}