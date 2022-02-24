package ge.bootcamp.travel19.feature_covid_restrictions.domain.model.country_restrictions


import com.squareup.moshi.Json

data class DiseaseInfection(
    @Json(name = "date")
    val date: String?,
    @Json(name = "infectionMapLink")
    val infectionMapLink: String?,
    @Json(name = "level")
    val level: String?,
    @Json(name = "rate")
    val rate: Double?
)