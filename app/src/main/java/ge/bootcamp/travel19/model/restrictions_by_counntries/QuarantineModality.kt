package ge.bootcamp.travel19.model.restrictions_by_counntries


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