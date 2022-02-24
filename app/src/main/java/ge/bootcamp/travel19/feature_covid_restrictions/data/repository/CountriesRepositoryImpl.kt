package ge.bootcamp.travel19.feature_covid_restrictions.data.repository

import ge.bootcamp.travel19.feature_covid_restrictions.data.remote_data_source.CountriesService
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.countriesv3.V3CountriesItem
import ge.bootcamp.travel19.feature_covid_restrictions.domain.repository.CountriesRepository
import ge.bootcamp.travel19.utils.ConnectionListener
import ge.bootcamp.travel19.utils.Constants.NO_INTERNET_CONNECTION
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.Resource
import retrofit2.Response

class CountriesRepositoryImpl (
    private val countriesApi: CountriesService,
    private var connectionListener: ConnectionListener
): CountriesRepository {

    override suspend fun getCountryByName(name: String): Resource<List<V3CountriesItem>> {
        return handleCountryResponse { countriesApi.getCountryByName(name) }
    }

    override suspend fun getAllCountry(): Resource<List<V3CountriesItem>> {
        return handleCountryResponse { countriesApi.getAllCountries() }
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
                Resource.Error(NO_INTERNET_CONNECTION)

        } catch (e: Throwable) {
            Resource.Error(e.message.toString(), null)
        }
    }

}

