package ge.bootcamp.travel19.domain.model.auth


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "vaccine")
    val vaccine: String?,
    val nationalities: String?
)