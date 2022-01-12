package ge.bootcamp.travel19.model.restrictions_by_counntries


import com.squareup.moshi.Json

data class Meta(
    @Json(name = "links")
    val links: Links?
)