package ge.bootcamp.travel19.model.restrictions_by_counntries


import com.squareup.moshi.Json

data class ValidityPeriod(
    @Json(name = "delay")
    val delay: String?,
    @Json(name = "referenceDateType")
    val referenceDateType: String?
)