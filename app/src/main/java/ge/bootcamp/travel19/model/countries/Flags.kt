package ge.bootcamp.travel19.model.countries


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Flags(
    @Json(name = "png")
    val png: String?,
    @Json(name = "svg")
    val svg: String?
) : Parcelable