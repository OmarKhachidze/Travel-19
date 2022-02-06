package ge.bootcamp.travel19.domain.model.country_restrictions


import com.squareup.moshi.Json

data class Entry(
    @Json(name = "ban")
    val ban: String?,
    @Json(name = "borderBan")
    val borderBan: List<BorderBan>?,
    @Json(name = "date")
    val date: String?,
    @Json(name = "rules")
    val rules: String?,
    @Json(name = "text")
    val text: String?
)