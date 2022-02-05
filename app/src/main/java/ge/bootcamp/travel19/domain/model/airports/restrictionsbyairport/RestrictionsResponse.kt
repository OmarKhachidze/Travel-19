package ge.bootcamp.travel19.domain.model.airports.restrictionsbyairport


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RestrictionsResponse(
    @Json(name = "restricions")
    val restricions: Map<String, GVA>,
    @Json(name = "success")
    val success: Boolean?
)