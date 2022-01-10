package ge.bootcamp.travel19.data.repository

import android.util.Log
import android.util.Log.d
import ge.bootcamp.travel19.data.remote.countries.CountriesDataSource
import ge.bootcamp.travel19.model.countries.Countries
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
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
                }else {
                    emit(Resource.Error(result.message(), null))
                }
            } catch (e: Throwable) {
                Log.i("onOtherErr", e.message.toString())
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
                    emit(Resource.Error(result.message(), null))
                }
            } catch (e: Throwable) {
                emit(Resource.Error("Country not found !", null))
            }
        }.flowOn(Dispatchers.IO)
    }
}
