package ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Airports(
    @Json(name = "airports")
    val airports: List<Airport>?,
    @Json(name = "success")
    val success: Boolean?
)