package pe.com.test.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.com.test.R
import pe.com.test.models.MoviePopular
import pe.com.test.models.MovieUpcoming
import pe.com.test.usecase.MoviesUseCases

class MyViewModel(application: Application) : AndroidViewModel(application) {

    val data = MutableLiveData<List<MoviePopular>>()
    val movieUpcoming = MutableLiveData<List<MovieUpcoming?>>()
    val error = MutableLiveData<String>()

    @SuppressLint("StaticFieldLeak")
    val context: Context = getApplication<Application>().applicationContext

    fun data() {

        viewModelScope.launch {

            val response = MoviesUseCases().getPopularMovies()
            if (response.isSuccessful) {
                data.value = response.body()!!.results
            } else {
                error.value = context.getString(R.string.errorSearch)
            }

            val movieUpcomingResponse = MoviesUseCases().getUpcomingMovies()
            if (response.isSuccessful) {
                movieUpcoming.value = movieUpcomingResponse.body()!!.results
            } else {
                error.value = context.getString(R.string.errorSearch)
            }
        }
    }

}