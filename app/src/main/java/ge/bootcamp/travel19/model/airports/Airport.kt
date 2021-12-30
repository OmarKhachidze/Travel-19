package ge.bootcamp.travel19.model.airports


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Airport(
    @Json(name = "city")
    val city: String?,
    @Json(name = "code")
    val code: String?,
    @Json(name = "country")
    val country: String?
)