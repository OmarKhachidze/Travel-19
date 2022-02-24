package ge.bootcamp.travel19.feature_covid_restrictions.domain.model.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponse(
    @Json(name = "success")
    val success: Boolean?,
    @Json(name = "token")
    val token: String?,
    @Json(name = "user")
    val user: User?
)

