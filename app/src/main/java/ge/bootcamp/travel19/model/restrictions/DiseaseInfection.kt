package ge.bootcamp.travel19.model.restrictions


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