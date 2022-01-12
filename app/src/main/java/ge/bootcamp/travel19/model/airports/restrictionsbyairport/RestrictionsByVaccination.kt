package ge.bootcamp.travel19.model.airports.restrictionsbyairport


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RestrictionsByVaccination(
    @Json(name = "dozesRequired")
    val dozesRequired: Int?,
    @Json(name = "isAllowed")
    val isAllowed: Boolean?,
    @Json(name = "maxDaysAfterVaccination")
    val maxDaysAfterVaccination: Int?,
    @Json(name = "minDaysAfterVaccination")
    val minDaysAfterVaccination: Int?
)