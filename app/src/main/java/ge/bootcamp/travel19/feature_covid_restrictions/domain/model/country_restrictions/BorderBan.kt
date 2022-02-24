package ge.bootcamp.travel19.feature_covid_restrictions.domain.model.country_restrictions


import com.squareup.moshi.Json

data class BorderBan(
    @Json(name = "borderType")
    val borderType: String?,
    @Json(name = "status")
    val status: String?
)