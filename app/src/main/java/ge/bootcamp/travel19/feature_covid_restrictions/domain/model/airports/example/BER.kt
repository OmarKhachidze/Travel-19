package ge.bootcamp.travel19.feature_covid_restrictions.domain.model.airports.example


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BER(
    @Json(name = "generalRestrictions")
    val generalRestrictions: GeneralRestrictions?,
    @Json(name = "type")
    val type: String?
)