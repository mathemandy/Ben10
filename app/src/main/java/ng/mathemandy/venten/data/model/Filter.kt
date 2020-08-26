package ng.mathemandy.venten.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Filter(
    val id: Int,
    val start_year: Int,
    val end_year: Int,
    val gender: String,
    val countries: List<String>,
    val colors: List<String>

) : Parcelable