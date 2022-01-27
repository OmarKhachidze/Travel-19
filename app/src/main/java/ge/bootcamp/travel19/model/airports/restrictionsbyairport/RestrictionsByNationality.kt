package ge.bootcamp.travel19.model.airports.restrictionsbyairport


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RestrictionsByNationality(
    @Json(name = "data")
    val `data`: Data?,
    @Json(name = "type")
    val type: String?
)