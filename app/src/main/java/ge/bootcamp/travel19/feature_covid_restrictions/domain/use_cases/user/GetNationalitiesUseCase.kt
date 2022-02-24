package ge.bootcamp.travel19.feature_covid_restrictions.domain.use_cases.user

import ge.bootcamp.travel19.feature_covid_restrictions.domain.repository.UserRepository
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.nationality.Nationalities
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNationalitiesUseCase(private val userRepository: UserRepository) {
    operator fun invoke(): Flow<Resource<Nationalities>> = flow {
        emit(Resource.Loading())
        emit(userRepository.getNationalities())
    }
}