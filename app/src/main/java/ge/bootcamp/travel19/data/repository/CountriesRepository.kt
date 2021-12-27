package ge.bootcamp.travel19.data.repository

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
                    emit(Resource.Error(result.message()))
                }
            } catch (e: Throwable) {
                emit(Resource.Error("Something went wrong!", null))
            }
        }.flowOn(Dispatchers.IO)
    }
}
