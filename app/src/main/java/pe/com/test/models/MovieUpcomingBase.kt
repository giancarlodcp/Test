package pe.com.test.models

import com.google.gson.annotations.SerializedName
import pe.com.test.models.MovieUpcoming

class MovieUpcomingBase(
    val page: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
    val results: List<MovieUpcoming>
)