package ge.bootcamp.travel19.data.repository

import ge.bootcamp.travel19.data.remote.user_info.UserInfoDataSource
import ge.bootcamp.travel19.model.airports.Airports
import ge.bootcamp.travel19.model.nationality.Nationalities
import ge.bootcamp.travel19.model.vaccines.Vaccines
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class UserInfoRepository @Inject constructor(
        private val userDataSource: UserInfoDataSource,
) {

    fun getVaccines(): Flow<Resource<Vaccines>> {
        return flow {
            emit(handleResponse { userDataSource.getVaccines() })
        }.flowOn(Dispatchers.IO)
    }

    fun getNationalities(): Flow<Resource<Nationalities>> {
        return flow {
            emit(handleResponse { userDataSource.getNationalities() })
        }
    }


    fun getAllAirport(): Flow<Resource<Airports>> {
        return flow {
            emit(handleAirportsResponse { userDataSource.fetchAirports() })
        }.flowOn(Dispatchers.IO)
    }

}


suspend fun <M> handleResponse(
        request: suspend () -> Response<M>
): Resource<M> {
    return try {
        Resource.Loading(null)
        val result = request.invoke()
        val body = result.body()
        if (result.isSuccessful && body != null) {
            return Resource.Success(body)
        } else {
            Resource.Error(result.message())
        }
    } catch (e: Throwable) {

        Resource.Error(e.message.toString(), null)
    }
}