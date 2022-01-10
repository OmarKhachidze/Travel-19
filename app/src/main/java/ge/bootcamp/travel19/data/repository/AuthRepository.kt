package ge.bootcamp.travel19.data.repository

import android.util.Log
import ge.bootcamp.travel19.data.remote.authentication.AuthDataSource
import ge.bootcamp.travel19.model.logIn.LoginRequest
import ge.bootcamp.travel19.model.singup.SignUpResponse
import ge.bootcamp.travel19.model.singup.UserInfo
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val apiAuth: AuthDataSource
) {
    fun signUp(userInfo: UserInfo): Flow<Resource<SignUpResponse>> {
        return flow {
            emit(handleResponse { apiAuth.signUp(userInfo) })
        }.flowOn(Dispatchers.IO)
    }

    fun logIn(login: LoginRequest): Flow<Resource<out SignUpResponse>> {
        return flow {
            try {
                emit(Resource.Loading(null))
                val result = apiAuth.logIn(login)
                val body = result.body()
                if (result.isSuccessful && body != null) {
                    emit(Resource.Success(body))
                } else if (result.code() == 401) {
                    val jObjError = JSONObject(result.errorBody()!!.charStream().readText())
                    emit(Resource.Error(jObjError.getString("error")))
                } else {
                    emit(Resource.Error(result.message(), null))
                }
            } catch (e: Throwable) {
                Log.i("onOtherErr", e.message.toString())
                emit(Resource.Error("Something went Wrong !"))
            }
        }.flowOn(Dispatchers.IO)
    }
}