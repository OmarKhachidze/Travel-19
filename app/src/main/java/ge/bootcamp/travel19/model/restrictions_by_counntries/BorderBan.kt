package ge.bootcamp.travel19.model.restrictions_by_counntries


import com.squareup.moshi.Json

data class BorderBan(
    @Json(name = "borderType")
    val borderType: String?,
    @Json(name = "status")
    val status: String?
)