package ge.bootcamp.travel19.model.airports.restrictionsbyairport


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "allowsBusinessVisit")
    val allowsBusinessVisit: Boolean?,
    @Json(name = "allowsTourists")
    val allowsTourists: Boolean?,
    @Json(name = "biometricPassportRequired")
    val biometricPassportRequired: Boolean?,
    @Json(name = "covidPassportRequired")
    val covidPassportRequired: Boolean?,
    @Json(name = "fastTestRequired")
    val fastTestRequired: Boolean?,
    @Json(name = "locatorFormRequired")
    val locatorFormRequired: Boolean?,
    @Json(name = "pcrRequired")
    val pcrRequired: Boolean?
)