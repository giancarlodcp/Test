package pe.com.test

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://api.themoviedb.org"

object ApiManager {

    private var apiInterface: ApiInterface? = null

    val retrofit: Retrofit
        get() {

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    fun get(): ApiInterface {
        apiInterface = retrofit.create(ApiInterface::class.java)
        return apiInterface!!
    }

}

interface ApiInterface {

    @GET("3/movie/popular")
    suspend fun popularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: String,
        @Query("language") language: String
    ): Response<MoviePopularBase>

    @GET("3/movie/upcoming")
    suspend fun upcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: String,
        @Query("language") language: String
    ): Response<MovieUpcomingBase>
}