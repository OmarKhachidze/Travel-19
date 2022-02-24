package ge.bootcamp.travel19.feature_covid_restrictions.domain.model.countriesv3


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class V3CountriesItem(
    @Json(name = "cca2")
    val cca2: String?,
    @Json(name = "flags")
    val flags: Flags?,
    @Json(name = "name")
    val name: Name?,
): Parcelable