package ge.bootcamp.travel19.data.repository

import ge.bootcamp.travel19.data.remote.authentication.AuthDataSource
import ge.bootcamp.travel19.model.auth.AuthResponse
import ge.bootcamp.travel19.model.auth.UserInfo
import ge.bootcamp.travel19.utils.ConnectionListener
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
    fun signUp(userInfo: UserInfo): Flow<Resource<out AuthResponse>> {
        return flow {
            emit(Resource.Loading(null))
            emit(handleAuthResponse { apiAuth.signUp(userInfo) })
        }.flowOn(Dispatchers.IO)
    }

    fun logIn(login: UserInfo): Flow<Resource<out AuthResponse>> {
        return flow {
            emit(Resource.Loading(null))
            emit(handleAuthResponse { apiAuth.logIn(login) })
        }.flowOn(Dispatchers.IO)
    }

    fun getSelf(token: String): Flow<Resource<out AuthResponse>> {
        return flow {
            emit(Resource.Loading(null))
            emit(handleAuthResponse { apiAuth.geSelf(token) })
        }.flowOn(Dispatchers.IO)
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
                    Resource.Error(jObjError.getString("error"))
                } else {
                    Resource.Error(result.message())
                }
            } else
                Resource.Error("No internet connection!")
        } catch (e: Throwable) {
            Resource.Error(e.message.toString(), null)
        }
    }
}
