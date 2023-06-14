package pe.com.test

import com.google.gson.annotations.SerializedName

class MoviePopularBase(
    val page: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
    val results: List<MoviePopular>
)