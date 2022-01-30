package ge.bootcamp.travel19.data.repository

import ge.bootcamp.travel19.data.remote.user_info.UserInfoDataSource
import ge.bootcamp.travel19.model.airports.Airports
import ge.bootcamp.travel19.model.nationality.Nationalities
import ge.bootcamp.travel19.model.vaccines.Vaccines
import ge.bootcamp.travel19.utils.ConnectionListener
import ge.bootcamp.travel19.utils.Constants
import ge.bootcamp.travel19.utils.Constants.NO_INTERNET_CONNECTION
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class UserInfoRepository @Inject constructor(
    private val userDataSource: UserInfoDataSource,
    private var connectionListener: ConnectionListener
) {

    val getVaccines: Flow<Resource<Vaccines>> = flow {
        emit(handleUserResponse { userDataSource.getVaccines() })
    }

    val getNationalities: Flow<Resource<Nationalities>> = flow {
        emit(handleUserResponse { userDataSource.getNationalities() })
    }

    val getAllAirport: Flow<Resource<Airports>> = flow {
        emit(handleUserResponse { userDataSource.fetchAirports() })
    }

    private suspend fun <M> handleUserResponse(
        request: suspend () -> Response<M>
    ): Resource<M> {
        return try {
            if (connectionListener.value == true) {
                val result = request.invoke()
                val body = result.body()
                if (result.isSuccessful && body != null) {
                    return Resource.Success(body)
                } else {
                    Resource.Error(result.message())
                }
            } else
                Resource.Error(NO_INTERNET_CONNECTION)
        } catch (e: Throwable) {
            Resource.Error(e.message.toString(), null)
        }
    }
}
