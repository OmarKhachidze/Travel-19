package ge.bootcamp.travel19.model.token


import com.squareup.moshi.Json

data class Success(
    @Json(name = "success")
    val success: Boolean?
)