package ge.bootcamp.travel19.model.country_restrictions


import com.squareup.moshi.Json

data class Links(
    @Json(name = "self")
    val self: String?
)