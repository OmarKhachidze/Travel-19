package ge.bootcamp.travel19.model.logIn

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    val email:String?,
    val password:String?,
)