package ge.bootcamp.travel19.data.repository

import ge.bootcamp.travel19.data.remote.AirportsDataSource
import ge.bootcamp.travel19.data.remote.RestrictionsByAirportDataSource
import ge.bootcamp.travel19.model.airports.Airports
import ge.bootcamp.travel19.model.airports.restrictionsbyairport.RestrictionsResponse
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class AirportsRepository @Inject constructor(private val dataSource: AirportsDataSource,
                                             private val restDataSource: RestrictionsByAirportDataSource
){

    fun getAllAirport(): Flow<Resource<Airports>> {
        return flow {
            emit(handleAirportsResponse { dataSource.fetchAirports()})
        }.flowOn(Dispatchers.IO)
    }

    fun getRestrictionsByAirport(loc:String, dest: String): Flow<Resource<RestrictionsResponse>> {
        return flow {
            emit(handleAirportsResponse { restDataSource.getRestByAirport(loc, dest)})
        }.flowOn(Dispatchers.IO)
    }
}

suspend fun <M> handleAirportsResponse(
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