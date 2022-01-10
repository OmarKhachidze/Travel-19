package ge.bootcamp.travel19.model.restrictions_by_counntries


import com.squareup.moshi.Json

data class DiseaseTesting(
    @Json(name = "date")
    val date: String?,
    @Json(name = "isRequired")
    val isRequired: String?,
    @Json(name = "requirement")
    val requirement: String?,
    @Json(name = "rules")
    val rules: String?,
    @Json(name = "testType")
    val testType: String?,
    @Json(name = "text")
    val text: String?,
    @Json(name = "validityPeriod")
    val validityPeriod: ValidityPeriod?,
    @Json(name = "when")
    val whenX: String?
)