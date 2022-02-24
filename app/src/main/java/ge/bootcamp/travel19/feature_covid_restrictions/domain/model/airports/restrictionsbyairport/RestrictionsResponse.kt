package ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.restrictionsbyairport


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RestrictionsResponse(
    @Json(name = "restricions")
    val restricions: Map<String, AirportRestricion>,
    @Json(name = "success")
    val success: Boolean?
)