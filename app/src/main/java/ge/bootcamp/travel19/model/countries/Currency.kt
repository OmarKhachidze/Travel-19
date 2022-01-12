package ge.bootcamp.travel19.model.countries


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Currency(
    @Json(name = "code")
    val code: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "symbol")
    val symbol: String?
): Parcelable