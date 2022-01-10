package ge.bootcamp.travel19.model.restrictions_by_counntries


import com.squareup.moshi.Json

data class AreaRestriction(
    @Json(name = "date")
    val date: String?,
    @Json(name = "restrictionType")
    val restrictionType: String?,
    @Json(name = "text")
    val text: String?
)