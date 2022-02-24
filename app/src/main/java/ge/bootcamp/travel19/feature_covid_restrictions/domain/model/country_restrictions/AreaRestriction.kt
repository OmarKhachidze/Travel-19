package ge.bootcamp.travel19.feature_covid_restrictions.domain.model.country_restrictions


import com.squareup.moshi.Json

data class AreaRestriction(
    @Json(name = "date")
    val date: String?,
    @Json(name = "restrictionType")
    val restrictionType: String?,
    @Json(name = "text")
    val text: String?
)