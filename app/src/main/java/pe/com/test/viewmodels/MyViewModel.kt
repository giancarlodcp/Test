package pe.com.test.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import pe.com.test.R
import pe.com.test.ResponseEnum
import pe.com.test.models.MoviePopular
import pe.com.test.models.MovieUpcoming
import pe.com.test.repository.MoviesRepository

class MyViewModel(application: Application, private val repository: MoviesRepository) :
    AndroidViewModel(application) {

    val allPopularMovies: LiveData<List<MoviePopular>> = repository.allPopularMovies.asLiveData()
    val allUpcomingMovies: LiveData<List<MovieUpcoming>> = repository.allUpcomingMovies.asLiveData()
    val error = MutableLiveData<String>()

    @SuppressLint("StaticFieldLeak")
    val context: Context = getApplication<Application>().applicationContext

    fun init() {

        viewModelScope.launch {

            when(repository.getPopularMovies()){
                ResponseEnum.NETWORK_ERROR -> error.value = context.getString(R.string.errorNetwork)
                ResponseEnum.ERROR -> error.value = context.getString(R.string.errorSearch)
                else -> {}
            }

            when(repository.getUpcomingMovies()){
                ResponseEnum.NETWORK_ERROR -> error.value = context.getString(R.string.errorNetwork)
                ResponseEnum.ERROR -> error.value = context.getString(R.string.errorSearch)
                else -> {}
            }

        }
    }

}