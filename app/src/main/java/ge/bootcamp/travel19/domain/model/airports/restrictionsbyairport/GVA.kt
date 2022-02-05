package ge.bootcamp.travel19.domain.model.airports.restrictionsbyairport

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GVA(
    @Json(name = "generalRestrictions")
    val generalRestrictions: GeneralRestrictions?,
    @Json(name = "restrictionsByNationality")
    val restrictionsByNationality: List<RestrictionsByNationality>?,
    @Json(name = "restrictionsByVaccination")
    val restrictionsByVaccination: RestrictionsByVaccination?,
    @Json(name = "type")
    val type: String?
)