package ge.bootcamp.travel19.domain.model.country_restrictions


import com.squareup.moshi.Json

data class Exit(
    @Json(name = "date")
    val date: String?,
    @Json(name = "isBanned")
    val isBanned: String?,
    @Json(name = "specialRequirements")
    val specialRequirements: String?,
    @Json(name = "text")
    val text: String?
)