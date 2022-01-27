package ge.bootcamp.travel19.model.airports.example


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ge.bootcamp.travel19.model.airports.restrictionsbyairport.RestrictionsByNationality
import ge.bootcamp.travel19.model.airports.restrictionsbyairport.RestrictionsByVaccination

@JsonClass(generateAdapter = true)
data class GVA(
    @Json(name = "generalRestrictions")
    val generalRestrictions: GeneralRestrictionsX?,
    @Json(name = "restrictionsByNationality")
    val restrictionsByNationality: List<RestrictionsByNationality>?,
    @Json(name = "restrictionsByVaccination")
    val restrictionsByVaccination: RestrictionsByVaccination?,
    @Json(name = "type")
    val type: String?
)