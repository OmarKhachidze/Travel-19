package ge.bootcamp.travel19.model.airports.example


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AirportsRestriction(
    @Json(name = "restricions")
    val restricions: Restricions?,
    @Json(name = "success")
    val success: Boolean?
)