package ge.bootcamp.travel19.model.airports.restrictionsbyairport


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RestrictionsResponse(
    @Json(name = "restricions")
    val restricions: Map<String, Any>,
    @Json(name = "success")
    val success: Boolean?
)