package ge.bootcamp.travel19.data.repository

import ge.bootcamp.travel19.data.remote.countries.CountriesDataSource
import ge.bootcamp.travel19.model.countriesv3.V3CountriesItem
import ge.bootcamp.travel19.utils.ConnectionListener
import ge.bootcamp.travel19.utils.Constants.NO_INTERNET_CONNECTION
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject


class CountriesRepository @Inject constructor(
    private val dataSource: CountriesDataSource,
    private var connectionListener: ConnectionListener
) {

    fun getWantedCountry(name: String): Flow<Resource<List<V3CountriesItem>>> {
        return flow {
            emit(handleCountryResponse { dataSource.getCountry(name) })
        }.flowOn(Dispatchers.IO)
    }

//    suspend fun getWantedCountry(name: String) = handleCountryResponse { dataSource.getCountry(name) }

    val getAllCountry: Flow<Resource<List<V3CountriesItem>>> = flow {
        emit(handleCountryResponse { dataSource.getAllCountry() })
    }

    private suspend fun <M> handleCountryResponse(
        request: suspend () -> Response<M>
    ): Resource<M> {
        return try {
            Resource.Loading(null)
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

