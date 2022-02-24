package ge.bootcamp.travel19.feature_covid_restrictions.data.repository

import ge.bootcamp.travel19.feature_covid_restrictions.data.remote_data_source.AuthService
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.auth.AuthResponse
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.auth.UserInfo
import ge.bootcamp.travel19.feature_covid_restrictions.domain.repository.AuthRepository
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.Resource
import ge.bootcamp.travel19.utils.ConnectionListener
import ge.bootcamp.travel19.utils.Constants
import org.json.JSONObject
import retrofit2.Response

open class AuthRepositoryImpl (
    private val apiAuth: AuthService,
    private var connectionListener: ConnectionListener
) : AuthRepository {

    override suspend fun logIn(logIn: UserInfo): Resource<AuthResponse> {
        return handleAuthResponse { apiAuth.logIn(logIn) }
    }

    override suspend fun signUp(userInfo: UserInfo): Resource<AuthResponse> {
        return handleAuthResponse { apiAuth.singUp(userInfo) }
    }


    private suspend fun <M> handleAuthResponse(
        request: suspend () -> Response<M>
    ): Resource<M> {
        return try {
            if (connectionListener.value == true) {
                val result = request.invoke()
                val body = result.body()
                if (result.isSuccessful && body != null) {
                    Resource.Success(body)
                } else if (result.code() == 401 || result.code() == 400) {
                    val jObjError = JSONObject(result.errorBody()!!.charStream().readText())
                    Resource.Error(jObjError.getString(Constants.ERROR_JSON_NAME))
                } else {
                    Resource.Error(result.message())
                }
            } else
                Resource.Error(Constants.NO_INTERNET_CONNECTION)
        } catch (e: Throwable) {
            Resource.Error(e.message.toString(), null)
        }
    }

}