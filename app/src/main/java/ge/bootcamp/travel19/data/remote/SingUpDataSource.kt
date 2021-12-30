package ge.bootcamp.travel19.data.remote

import ge.bootcamp.travel19.data.remote.authentication.SignUpService
import ge.bootcamp.travel19.model.singup.UserInfo
import javax.inject.Inject

class SingUpDataSource @Inject constructor(private val api: SignUpService){
    suspend fun postUserInfo(userInfo: UserInfo) = api.singUp(userInfo)
}