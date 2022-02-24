package ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.example


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AirportsRestriction(
    @Json(name = "restricions")
    val restricions: Restricions?,
    @Json(name = "success")
    val success: Boolean?
)