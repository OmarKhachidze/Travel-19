package ge.bootcamp.travel19.domain.model.auth


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "data")
    val data: Data?,
    @Json(name = "email")
    val email: String?,
    @Json(name = "_id")
    val id: String?
)