package ge.bootcamp.travel19.model.countries


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Countries(
    @Json(name = "alpha2Code")
    val alpha2Code: String?,
    @Json(name = "flag")
    val flag: String?,
    @Json(name = "flags")
    val flags: Flags?,
    @Json(name = "latlng")
    val latlng: List<Double>?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "nativeName")
    val nativeName: String?,
    @Json(name = "numericCode")
    val numericCode: String?,
): Parcelable