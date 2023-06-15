package pe.com.test.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import okhttp3.Response
import pe.com.test.ApiManager
import pe.com.test.ResponseEnum
import pe.com.test.models.MoviePopular


class MoviesRepository(private val moviesDAO: MoviesDAO) {

    val allPopularMovies: Flow<List<MoviePopular>> = moviesDAO.getPopularMovies()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(): ResponseEnum {
        return try{
            val response = ApiManager.get().popularMovies("d9ae4921794c06bd0fdbd1463d274804", "1", "en-US")
            if (response.isSuccessful) {
                val movies = response.body()!!.results
                moviesDAO.insertMovies(movies)
                ResponseEnum.OK
            } else {
                ResponseEnum.ERROR
            }
        }catch (ex: Exception){
            ResponseEnum.NETWORK_ERROR
        }


    }
}