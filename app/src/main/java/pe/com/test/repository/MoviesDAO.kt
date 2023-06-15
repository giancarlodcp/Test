package pe.com.test.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pe.com.test.models.MoviePopular
import pe.com.test.models.MovieUpcoming

@Dao
interface MoviesDAO {

    @Query("SELECT * FROM popular_movies_table ORDER BY id ASC")
    fun getPopularMovies(): Flow<List<MoviePopular>>

    @Query("SELECT * FROM upcoming_movies_table ORDER BY id ASC")
    fun getUpcomingMovies(): Flow<List<MovieUpcoming>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: MoviePopular)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movie: List<MoviePopular>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUpcomingMovies(movie: List<MovieUpcoming>)

    @Query("DELETE FROM popular_movies_table")
    suspend fun deleteAll()
}