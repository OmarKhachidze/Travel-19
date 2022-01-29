package ge.bootcamp.travel19.data.repository

import ge.bootcamp.travel19.data.remote.countries.CountriesDataSource
import ge.bootcamp.travel19.model.countriesv3.V3CountriesItem
import ge.bootcamp.travel19.utils.ConnectionListener
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

    fun getWantedCountry(name: String): Flow<Resource<out List<V3CountriesItem>>> {
        return flow {
            emit(Resource.Loading(null))
            emit(handleCountryResponse{ dataSource.getCountry(name) })
        }.flowOn(Dispatchers.IO)
    }

    fun getEveryCountry(): Flow<Resource<out List<V3CountriesItem>>> {
        return flow {
            emit(Resource.Loading(null))
            emit(handleCountryResponse{ dataSource.getAllCountry() })
        }.flowOn(
            Dispatchers.IO
        )
    }
    private suspend fun <M> handleCountryResponse(
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
                Resource.Error("No internet connection!")

        } catch (e: Throwable) {
            Resource.Error(e.message.toString(), null)
        }
    }
}

