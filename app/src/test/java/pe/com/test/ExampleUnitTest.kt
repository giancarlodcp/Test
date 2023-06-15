package pe.com.test

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import pe.com.test.models.MoviePopular
import pe.com.test.repository.MoviesDAO
import pe.com.test.repository.MoviesRoomDatabase
import java.io.IOException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleUnitTest {

    private lateinit var dao: MoviesDAO
    private lateinit var db: MoviesRoomDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, MoviesRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.moviesDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun getAllMoviesPopular() = runBlocking {
        val movie1 = MoviePopular(0.0, 0, true, "", 1, true, "", "", "", "", 0.0, "", "")
        dao.insertMovie(movie1)
        val movie2 = MoviePopular(0.0, 0, true, "", 2, true, "", "", "", "", 0.0, "", "")
        dao.insertMovie(movie2)
        val allMovies = dao.getPopularMovies().first()
        assertEquals(allMovies[0].id, movie1.id)
        assertEquals(allMovies[1].id, movie2.id)
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

}