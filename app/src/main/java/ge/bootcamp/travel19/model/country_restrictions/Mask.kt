package ge.bootcamp.travel19.model.country_restrictions


import com.squareup.moshi.Json

data class Mask(
    @Json(name = "date")
    val date: String?,
    @Json(name = "isRequired")
    val isRequired: String?,
    @Json(name = "text")
    val text: String?
)