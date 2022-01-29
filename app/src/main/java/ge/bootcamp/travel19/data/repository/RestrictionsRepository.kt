package ge.bootcamp.travel19.data.repository

import ge.bootcamp.travel19.data.remote.restrictions.RestrictionsDataSource
import ge.bootcamp.travel19.model.airports.restrictionsbyairport.RestrictionsResponse
import ge.bootcamp.travel19.model.country_restrictions.CovidRestrictions
import ge.bootcamp.travel19.utils.ConnectionListener
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class RestrictionsRepository @Inject constructor(
    private val restrictionsDataSource: RestrictionsDataSource,
    private var connectionListener: ConnectionListener
) {
    fun getCovidRestrictions(countryCode: String): Flow<Resource<out CovidRestrictions>> {
        return flow {
            emit(Resource.Loading(null))
            emit(handleRestrictionsResponse { restrictionsDataSource.getRestrictions(countryCode) })
        }.flowOn(Dispatchers.IO)
    }

    fun getCovidRestrictionsTest(): Flow<Resource<out CovidRestrictions>> {
        return flow {
            emit(Resource.Loading(null))
            emit(handleRestrictionsResponse { restrictionsDataSource.getRestrictionsTest() })
        }.flowOn(Dispatchers.IO)
    }

    fun getRestrictionsByAirport(loc: String, dest: String): Flow<Resource<RestrictionsResponse>> {
        return flow {
            emit(handleRestrictionsResponse { restrictionsDataSource.getRestByAirport(loc, dest) })
        }.flowOn(Dispatchers.IO)
    }

    fun getRestrictionsByAirportUserInfo(
        loc: String,
        dest: String,
        nationality: String,
        vaccine: String
    ): Flow<Resource<out RestrictionsResponse>> {
        return flow {
            emit(Resource.Loading(null))
            emit(handleRestrictionsResponse {
                restrictionsDataSource.getRestByAirportWithUserInfo(
                    loc,
                    dest,
                    nationality,
                    vaccine
                )
            })
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun <M> handleRestrictionsResponse(
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

