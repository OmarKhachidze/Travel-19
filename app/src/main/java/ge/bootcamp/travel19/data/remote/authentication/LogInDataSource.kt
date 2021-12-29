package ge.bootcamp.travel19.data.remote.authentication

import ge.bootcamp.travel19.model.logIn.LoginRequest
import javax.inject.Inject

class LogInDataSource @Inject constructor(private val api: LogInService){
    suspend fun logIn(logIn:
                      LoginRequest
    ) = api.logIn(logIn)
}