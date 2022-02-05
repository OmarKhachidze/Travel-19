package ge.bootcamp.travel19.data.repository.user_info

import ge.bootcamp.travel19.data.remote.UserService
import ge.bootcamp.travel19.domain.model.airports.Airports
import ge.bootcamp.travel19.domain.model.auth.AuthResponse
import ge.bootcamp.travel19.domain.model.nationality.Nationalities
import ge.bootcamp.travel19.domain.model.vaccines.Vaccines
import ge.bootcamp.travel19.domain.states.Resource
import ge.bootcamp.travel19.utils.ConnectionListener
import ge.bootcamp.travel19.utils.Constants
import ge.bootcamp.travel19.utils.Constants.NO_INTERNET_CONNECTION
import org.json.JSONObject
import retrofit2.Response

class UserRepositoryImpl (
    private val userApi: UserService,
    private var connectionListener: ConnectionListener
) : UserRepository {

    override suspend fun getNationalities(): Resource<Nationalities> {
        return handleUserResponse { userApi.getNationalities() }
    }

    override suspend fun getVaccines(): Resource<Vaccines> {
        return handleUserResponse { userApi.getVaccine() }
    }

    override suspend fun getAirports(): Resource<Airports> {
        return handleUserResponse { userApi.getAirports() }
    }

    override suspend fun getUser(token: String): Resource<AuthResponse> {
        return handleUserResponse { userApi.getUser(token) }
    }

    private suspend fun <M> handleUserResponse(
        request: suspend () -> Response<M>
    ): Resource<M> {
        return try {
            if (connectionListener.value == true) {
                val result = request.invoke()
                val body = result.body()
                if (result.isSuccessful && body != null) {
                    return Resource.Success(body)
                } else if (result.code() == 401 || result.code() == 400) {
                    val jObjError = JSONObject(result.errorBody()!!.charStream().readText())
                    Resource.Error(jObjError.getString(Constants.ERROR_JSON_NAME))
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
