package pe.com.test.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "popular_movies_table")
class MoviePopular(
    val popularity: Double,
    @SerializedName("vote_count") val voteCount: Int,
    val video: Boolean,
    @ColumnInfo(name = "posterPath") @SerializedName("poster_path") val posterPath: String,
    @PrimaryKey val id: Int,
    val adult: Boolean,
    @ColumnInfo(name = "backdropPath") @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    //@Ignore @SerializedName("genre_ids") val genreIds: List<Int>,
    @ColumnInfo(name = "title") val title: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @ColumnInfo(name = "overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String
)
