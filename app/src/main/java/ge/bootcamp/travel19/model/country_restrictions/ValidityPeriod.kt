package ge.bootcamp.travel19.model.country_restrictions


import com.squareup.moshi.Json

data class ValidityPeriod(
    @Json(name = "delay")
    val delay: String?,
    @Json(name = "referenceDateType")
    val referenceDateType: String?
)