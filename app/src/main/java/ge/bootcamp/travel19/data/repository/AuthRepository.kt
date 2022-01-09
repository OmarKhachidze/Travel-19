package ge.bootcamp.travel19.data.repository

import ge.bootcamp.travel19.data.remote.authentication.AuthDataSource
import ge.bootcamp.travel19.model.logIn.LoginRequest
import ge.bootcamp.travel19.model.singup.SignUpResponse
import ge.bootcamp.travel19.model.singup.UserInfo
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val apiAuth: AuthDataSource
) {
    fun signUp(userInfo: UserInfo): Flow<Resource<SignUpResponse>> {
        return flow {
            emit(handleResponse { apiAuth.signUp(userInfo) })
        }.flowOn(Dispatchers.IO)
    }

    fun logIn(login: LoginRequest): Flow<Resource<SignUpResponse>> {
        return flow {
            emit(handleResponse { apiAuth.logIn(login) })
        }.flowOn(Dispatchers.IO)
    }
}