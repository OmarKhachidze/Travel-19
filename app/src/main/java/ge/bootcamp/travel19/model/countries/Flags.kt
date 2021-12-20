package ge.bootcamp.travel19.model.countries


import com.squareup.moshi.Json

data class Flags(
    @Json(name = "png")
    val png: String?,
    @Json(name = "svg")
    val svg: String?
)