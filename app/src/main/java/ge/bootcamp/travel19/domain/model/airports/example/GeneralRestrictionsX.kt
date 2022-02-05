package ge.bootcamp.travel19.domain.model.airports.example


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeneralRestrictionsX(
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