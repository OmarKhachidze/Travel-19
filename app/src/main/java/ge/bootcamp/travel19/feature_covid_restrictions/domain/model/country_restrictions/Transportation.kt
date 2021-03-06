package ge.bootcamp.travel19.feature_covid_restrictions.domain.model.country_restrictions


import com.squareup.moshi.Json

data class Transportation(
    @Json(name = "date")
    val date: String?,
    @Json(name = "isBanned")
    val isBanned: String?,
    @Json(name = "text")
    val text: String?,
    @Json(name = "transportationType")
    val transportationType: String?
)