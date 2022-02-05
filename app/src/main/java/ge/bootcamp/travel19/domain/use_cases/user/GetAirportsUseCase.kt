package ge.bootcamp.travel19.domain.use_cases.user

import ge.bootcamp.travel19.data.repository.user_info.UserRepository
import ge.bootcamp.travel19.domain.model.airports.Airports
import ge.bootcamp.travel19.domain.states.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAirportsUseCase(private val userRepository: UserRepository) {
    operator fun invoke(): Flow<Resource<Airports>> = flow {
        emit(Resource.Loading())
        emit(userRepository.getAirports())
    }
}