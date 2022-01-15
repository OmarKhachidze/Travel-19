package ge.bootcamp.travel19.data.remote.authentication

import ge.bootcamp.travel19.model.logIn.LoginRequest
import ge.bootcamp.travel19.model.singup.UserInfo
import javax.inject.Inject

class AuthDataSource @Inject constructor(private val api: AuthService) {
    suspend fun logIn(logIn: LoginRequest) = api.logIn(logIn)
    suspend fun signUp(userInfo: UserInfo) = api.singUp(userInfo)
    suspend fun geSelf(token:String) = api.getSelf(token)
}