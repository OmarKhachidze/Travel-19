package ge.bootcamp.travel19.model.logIn


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "vaccine")
    val vaccine: String?
)