package ge.bootcamp.travel19.model.restrictions_by_counntries


import com.squareup.moshi.Json

data class TracingApplication(
    @Json(name = "date")
    val date: String?,
    @Json(name = "isRequired")
    val isRequired: String?,
    @Json(name = "text")
    val text: String?
)