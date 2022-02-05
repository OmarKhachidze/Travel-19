package ge.bootcamp.travel19.data.repository.restrictions

import ge.bootcamp.travel19.data.remote.RestrictionsService
import ge.bootcamp.travel19.domain.model.airports.RestrictionByAirport
import ge.bootcamp.travel19.domain.model.airports.restrictionsbyairport.RestrictionsResponse
import ge.bootcamp.travel19.domain.model.country_restrictions.CovidRestrictions
import ge.bootcamp.travel19.domain.states.Resource
import ge.bootcamp.travel19.utils.ConnectionListener
import ge.bootcamp.travel19.utils.Constants.NO_INTERNET_CONNECTION
import retrofit2.Response

class RestrictionsRepositoryImpl (
    private val restrictionsApi: RestrictionsService,
    private var connectionListener: ConnectionListener
) : RestrictionsRepository {

    override suspend fun getRestrictionsByCountry(countryCode: String): Resource<CovidRestrictions> {
        return handleRestrictionsResponse {
            restrictionsApi.getCovidRestrictionsByCountry(
                countryCode
            )
        }
    }

    override suspend fun getRestrictionsTest(): Resource<CovidRestrictions> {
        return handleRestrictionsResponse { restrictionsApi.getCovidRestrictionsTest() }
    }

    override suspend fun getRestrictionByAirport(restrictionParam: RestrictionByAirport): Resource<RestrictionsResponse> {
        return handleRestrictionsResponse {
            with(restrictionParam) {
                restrictionsApi.getRestrictionByAirport(location, destination, nationality, vaccine)
            }
        }
    }

    private suspend fun <M> handleRestrictionsResponse(
        request: suspend () -> Response<M>
    ): Resource<M> {
        return try {
            if (connectionListener.value == true) {
                val result = request.invoke()
                val body = result.body()
                if (result.isSuccessful && body != null) {
                    Resource.Success(body)
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

