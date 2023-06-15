package pe.com.test.usecase

import pe.com.test.ApiManager
import pe.com.test.models.MoviePopularBase
import pe.com.test.models.MovieUpcomingBase
import retrofit2.Response

class MoviesUseCases {
    suspend fun getPopularMovies(): Response<MoviePopularBase> {
        return ApiManager.get().popularMovies("d9ae4921794c06bd0fdbd1463d274804", "1", "en-US")
    }

    suspend fun getUpcomingMovies(): Response<MovieUpcomingBase> {
        return ApiManager.get().upcomingMovies("d9ae4921794c06bd0fdbd1463d274804", "1", "en-US")
    }
}