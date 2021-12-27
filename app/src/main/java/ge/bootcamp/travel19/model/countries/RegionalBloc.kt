package ge.bootcamp.travel19.model.countries


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegionalBloc(
    @Json(name = "acronym")
    val acronym: String?,
    @Json(name = "name")
    val name: String?
) : Parcelable