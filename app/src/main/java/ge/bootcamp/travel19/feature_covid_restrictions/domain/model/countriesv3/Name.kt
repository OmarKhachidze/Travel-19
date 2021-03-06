package ge.bootcamp.travel19.feature_covid_restrictions.domain.model.countriesv3


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Name(
    @Json(name = "common")
    val common: String?,
    @Json(name = "official")
    val official: String?
): Parcelable