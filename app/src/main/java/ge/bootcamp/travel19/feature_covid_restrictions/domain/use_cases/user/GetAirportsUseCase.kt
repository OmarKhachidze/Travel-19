package ge.bootcamp.travel19.feature_covid_restrictions.domain.use_cases.user

import ge.bootcamp.travel19.feature_covid_restrictions.domain.repository.UserRepository
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.Airports
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAirportsUseCase(private val userRepository: UserRepository) {
    operator fun invoke(): Flow<Resource<Airports>> = flow {
        emit(Resource.Loading())
        emit(userRepository.getAirports())
    }
}