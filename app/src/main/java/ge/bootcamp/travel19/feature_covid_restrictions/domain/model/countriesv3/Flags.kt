package ge.bootcamp.travel19.feature_covid_restrictions.domain.model.countriesv3


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Flags(
    @Json(name = "png")
    val png: String?,
    @Json(name = "svg")
    val svg: String?
): Parcelable