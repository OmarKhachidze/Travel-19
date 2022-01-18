package ge.bootcamp.travel19.model.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ge.bootcamp.travel19.model.logIn.User

@JsonClass(generateAdapter = true)
data class AuthResponse(
    @Json(name = "success")
    val success: Boolean?,
    @Json(name = "token")
    val token: String?,
    @Json(name = "user")
    val user: User?
)