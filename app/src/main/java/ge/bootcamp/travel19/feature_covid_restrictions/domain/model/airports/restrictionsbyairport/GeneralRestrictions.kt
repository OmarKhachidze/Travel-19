package ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.restrictionsbyairport


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeneralRestrictions(
    @Json(name = "allowsBusinessVisit")
    val allowsBusinessVisit: Boolean?,
    @Json(name = "allowsTourists")
    val allowsTourists: Boolean?,
    @Json(name = "covidPassportRequired")
    val covidPassportRequired: Boolean?,
    @Json(name = "generalInformation")
    val generalInformation: String?,
    @Json(name = "moreInfoUrl")
    val moreInfoUrl: String?,
    @Json(name = "pcrRequiredForNoneResidents")
    val pcrRequiredForNoneResidents: Boolean?,
    @Json(name = "pcrRequiredForResidents")
    val pcrRequiredForResidents: Boolean?
)