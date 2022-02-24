package ge.bootcamp.travel19.feature_covid_restrictions.domain.model.country_restrictions


import com.squareup.moshi.Json

data class QuarantineModality(
    @Json(name = "date")
    val date: String?,
    @Json(name = "duration")
    val duration: Int?,
    @Json(name = "eligiblePerson")
    val eligiblePerson: String?,
    @Json(name = "quarantineOnArrivalAreas")
    val quarantineOnArrivalAreas: List<QuarantineOnArrivalArea>?,
    @Json(name = "quarantineType")
    val quarantineType: String?,
    @Json(name = "rules")
    val rules: String?,
    @Json(name = "text")
    val text: String?
)