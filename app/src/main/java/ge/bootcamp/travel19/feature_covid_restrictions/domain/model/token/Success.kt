package ge.bootcamp.travel19.feature_covid_restrictions.domain.model.token


import com.squareup.moshi.Json

data class Success(
    @Json(name = "success")
    val success: Boolean?,
    val position: Int?
)