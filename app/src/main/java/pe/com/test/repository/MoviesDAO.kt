package pe.com.test.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pe.com.test.models.MoviePopular

@Dao
interface MoviesDAO {

    @Query("SELECT * FROM popular_movies_table ORDER BY id ASC")
    fun getPopularMovies(): Flow<List<MoviePopular>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: MoviePopular)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movie: List<MoviePopular>)

    @Query("DELETE FROM popular_movies_table")
    suspend fun deleteAll()
}