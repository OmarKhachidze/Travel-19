package ge.bootcamp.travel19.data.repository

import ge.bootcamp.travel19.data.remote.RestrictionsDataSource
import ge.bootcamp.travel19.model.restrictions.CovidRestrictions
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class RestrictionsRepository @Inject constructor(private val dataSource: RestrictionsDataSource) {
    fun getCovidRestrictions(countryCode: String): Flow<Resource<CovidRestrictions>> {
        return flow {
            emit(handleResponse { dataSource.getRestrictions(countryCode) })
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
        Resource.Error("Something went wrong!", null)
    }
}