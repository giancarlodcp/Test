package pe.com.test.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import pe.com.test.ApiManager
import pe.com.test.ResponseEnum
import pe.com.test.models.MoviePopular
import pe.com.test.models.MovieUpcoming

class MoviesRepository(private val moviesDAO: MoviesDAO) {

    val allPopularMovies: Flow<List<MoviePopular>> = moviesDAO.getPopularMovies()
    val allUpcomingMovies: Flow<List<MovieUpcoming>> = moviesDAO.getUpcomingMovies()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getPopularMovies(): ResponseEnum {
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


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getUpcomingMovies(): ResponseEnum {
        return try {
            val response =
                ApiManager.get().upcomingMovies("d9ae4921794c06bd0fdbd1463d274804", "1", "en-US")
            if (response.isSuccessful) {
                val movies = response.body()!!.results
                moviesDAO.insertUpcomingMovies(movies)
                ResponseEnum.OK
            } else {
                ResponseEnum.ERROR
            }
        } catch (ex: Exception) {
            ResponseEnum.NETWORK_ERROR
        }
    }
}