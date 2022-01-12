package ge.bootcamp.travel19.model.restrictions_by_counntries


import com.squareup.moshi.Json

data class DiseaseCases(
    @Json(name = "confirmed")
    val confirmed: Int?,
    @Json(name = "date")
    val date: String?,
    @Json(name = "deaths")
    val deaths: Int?
)