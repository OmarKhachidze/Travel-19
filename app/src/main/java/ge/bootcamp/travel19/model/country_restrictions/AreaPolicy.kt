package ge.bootcamp.travel19.model.country_restrictions


import com.squareup.moshi.Json

data class AreaPolicy(
    @Json(name = "date")
    val date: String?,
    @Json(name = "endDate")
    val endDate: String?,
    @Json(name = "referenceLink")
    val referenceLink: String?,
    @Json(name = "startDate")
    val startDate: String?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "text")
    val text: String?
)