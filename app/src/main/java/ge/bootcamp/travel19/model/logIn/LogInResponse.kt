package ge.bootcamp.travel19.model.logIn


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LogInResponse(
    @Json(name = "success")
    val success: Boolean?,
    @Json(name = "token")
    val token: String?,
    @Json(name = "user")
    val user: User?
)