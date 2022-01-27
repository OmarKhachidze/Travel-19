package ge.bootcamp.travel19.model.country_restrictions


import com.squareup.moshi.Json

data class DiseaseCases(
    @Json(name = "confirmed")
    val confirmed: Int?,
    @Json(name = "date")
    val date: String?,
    @Json(name = "deaths")
    val deaths: Int?
)