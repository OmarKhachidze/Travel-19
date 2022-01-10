package ge.bootcamp.travel19.data.repository

import android.util.Log
import ge.bootcamp.travel19.data.remote.restrictions.RestrictionsDataSource
import ge.bootcamp.travel19.model.airports.Airports
import ge.bootcamp.travel19.model.airports.restrictionsbyairport.RestrictionsResponse
import ge.bootcamp.travel19.model.restrictions_by_counntries.CovidRestrictions
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

import org.json.JSONObject


class RestrictionsRepository @Inject constructor(
    private val restrictionsDataSource: RestrictionsDataSource,
) {
    fun getCovidRestrictions(countryCode: String): Flow<Resource<CovidRestrictions>> {
        return flow {
            emit(handleResponse { restrictionsDataSource.getRestrictions(countryCode) })
        }.flowOn(Dispatchers.IO)
    }

    fun getRestrictionsByAirport(loc: String, dest: String): Flow<Resource<RestrictionsResponse>> {
        return flow {
            emit(handleAirportsResponse { restrictionsDataSource.getRestByAirport(loc, dest) })
        }.flowOn(Dispatchers.IO)
    }

    fun getAllAirport(): Flow<Resource<Airports>> {
        return flow {
            emit(handleAirportsResponse { restrictionsDataSource.fetchAirports() })
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
        } else if (result.code() == 400) {
            val jObjError = JSONObject(result.errorBody()!!.charStream().readText())
            Resource.Error(jObjError.getJSONArray("errors").getJSONObject(0).getString("detail"))
        } else {
            Log.i("onError", result.message())
            Resource.Error(result.message())
        }
    } catch (e: Throwable) {
        Log.i("onOtherErr", e.message.toString())
        Resource.Error("Something went wrong!", null)
    }
}
