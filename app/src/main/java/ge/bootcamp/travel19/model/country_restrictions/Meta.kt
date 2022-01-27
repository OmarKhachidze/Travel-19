package ge.bootcamp.travel19.model.country_restrictions


import com.squareup.moshi.Json

data class Meta(
    @Json(name = "links")
    val links: Links?
)