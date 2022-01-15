package ge.bootcamp.travel19.model.getSelf


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Self(
    @Json(name = "success")
    val success: Boolean?,
    @Json(name = "user")
    val user: User?
)