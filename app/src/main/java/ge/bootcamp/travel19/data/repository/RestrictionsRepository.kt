package ge.bootcamp.travel19.data.repository

import ge.bootcamp.travel19.data.remote.restrictions.RestrictionsDataSource
import ge.bootcamp.travel19.model.airports.restrictionsbyairport.RestrictionsResponse
import ge.bootcamp.travel19.model.country_restrictions.CovidRestrictions
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class RestrictionsRepository @Inject constructor(
        private val restrictionsDataSource: RestrictionsDataSource,
) {
    fun getCovidRestrictions(countryCode: String): Flow<Resource<CovidRestrictions>> {
        return flow {
            emit(handleAirportsResponse { restrictionsDataSource.getRestrictions(countryCode) })
        }.flowOn(Dispatchers.IO)
    }

    fun getCovidRestrictionsTest(): Flow<Resource<CovidRestrictions>> {
        return flow {
            emit(handleAirportsResponse { restrictionsDataSource.getRestrictionsTest() })
        }.flowOn(Dispatchers.IO)
    }

    fun getRestrictionsByAirport(loc: String, dest: String): Flow<Resource<RestrictionsResponse>> {
        return flow {
            emit(handleAirportsResponse { restrictionsDataSource.getRestByAirport(loc, dest) })
        }.flowOn(Dispatchers.IO)
    }

    fun getRestrictionsByAirportUserInfo(loc: String, dest: String, nationality: String, vaccine: String): Flow<Resource<RestrictionsResponse>> {
        return flow {
            emit(handleAirportsResponse { restrictionsDataSource.getRestByAirportWithUserInfo(loc, dest, nationality, vaccine) })
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
