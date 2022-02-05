package ge.bootcamp.travel19.domain.use_cases.user

import ge.bootcamp.travel19.data.repository.user_info.UserRepository
import ge.bootcamp.travel19.domain.model.nationality.Nationalities
import ge.bootcamp.travel19.domain.states.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNationalitiesUseCase(private val userRepository: UserRepository) {
    operator fun invoke(): Flow<Resource<Nationalities>> = flow {
        emit(Resource.Loading())
        emit(userRepository.getNationalities())
    }
}