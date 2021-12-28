package ge.bootcamp.travel19.data.repository

import android.util.Log
import ge.bootcamp.travel19.data.remote.countries.CountriesDataSource
import ge.bootcamp.travel19.model.countries.Countries
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject


class CountriesRepository @Inject constructor(private val dataSource: CountriesDataSource) {
    fun getWantedCountry(name: String): Flow<Resource<out List<Countries>>> {
        return flow {
            try {
                emit(Resource.Loading(null))
                val result = dataSource.getCountry(name)
                val body = result.body()
                if (result.isSuccessful && body != null) {
                    emit(Resource.Success(body))
                } else {

                    val jsonObj =
                        org.json.JSONObject(result.errorBody()!!.charStream().readText())
                    Log.d("awdawdawdawdawd", jsonObj.getString("message"))

                    emit(Resource.Error(jsonObj.getString("message")))

                }
            } catch (e: Throwable) {
                emit(Resource.Error("Country not found !"))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getEveryCountry(): Flow<Resource<out List<Countries>>> {
        return flow {
            try {
                emit(Resource.Loading(null))
                val result = dataSource.getAllCountry()
                val body = result.body()
                if (result.isSuccessful && body != null) {
                    emit(Resource.Success(body))
                } else {

                    val jsonObj =
                        org.json.JSONObject(result.errorBody()!!.charStream().readText())
                    Log.d("awdawdawdawdawd", jsonObj.getString("message"))

                    emit(Resource.Error(jsonObj.getString("message")))

                }
            } catch (e: Throwable) {
                emit(Resource.Error("Country not found !"))
            }
        }.flowOn(Dispatchers.IO)
    }
}
