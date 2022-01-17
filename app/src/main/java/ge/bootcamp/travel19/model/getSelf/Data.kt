package ge.bootcamp.travel19.model.getSelf


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "nationalities")
    val nationalities: String?,
    @Json(name = "vaccine")
    val vaccine: String?
)