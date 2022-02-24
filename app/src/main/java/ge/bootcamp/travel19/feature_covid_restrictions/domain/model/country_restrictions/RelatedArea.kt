package ge.bootcamp.travel19.feature_covid_restrictions.domain.model.country_restrictions


import com.squareup.moshi.Json

data class RelatedArea(
    @Json(name = "methods")
    val methods: List<String>?,
    @Json(name = "rel")
    val rel: String?
)