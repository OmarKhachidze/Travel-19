package ge.bootcamp.travel19.data.repository

import ge.bootcamp.travel19.data.remote.authentication.AuthDataSource
import ge.bootcamp.travel19.model.auth.AuthResponse
import ge.bootcamp.travel19.model.auth.UserInfo
import ge.bootcamp.travel19.utils.ConnectionListener
import ge.bootcamp.travel19.utils.Constants.ERROR_JSON_NAME
import ge.bootcamp.travel19.utils.Constants.NO_INTERNET_CONNECTION
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val apiAuth: AuthDataSource,
    private var connectionListener: ConnectionListener
) {
    fun signUp(userInfo: UserInfo): Flow<Resource<AuthResponse>> {
        return flow {
            emit(handleAuthResponse { apiAuth.signUp(userInfo) })
        }
    }

    fun logIn(login: UserInfo): Flow<Resource<AuthResponse>> {
        return flow {
            emit(handleAuthResponse { apiAuth.logIn(login) })
        }
    }

    fun getSelf(token: String): Flow<Resource<AuthResponse>> {
        return flow {
            emit(handleAuthResponse { apiAuth.geSelf(token) })
        }
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
                    Resource.Error(jObjError.getString(ERROR_JSON_NAME))
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
