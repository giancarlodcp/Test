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
import pe.com.test.usecase.MoviesUseCases

class MyViewModel(application: Application, private val repository: MoviesRepository) :
    AndroidViewModel(application) {

    val allPopularMovies: LiveData<List<MoviePopular>> = repository.allPopularMovies.asLiveData()
    val movieUpcoming = MutableLiveData<List<MovieUpcoming?>>()
    val error = MutableLiveData<String>()

    @SuppressLint("StaticFieldLeak")
    val context: Context = getApplication<Application>().applicationContext

    fun init() {

        viewModelScope.launch {

            when(repository.insert()){
                ResponseEnum.NETWORK_ERROR -> error.value = context.getString(R.string.errorNetwork)
                ResponseEnum.ERROR -> error.value = context.getString(R.string.errorSearch)
                else -> {}
            }

            try {
                val movieUpcomingResponse = MoviesUseCases().getUpcomingMovies()
                if (movieUpcomingResponse.isSuccessful) {
                    movieUpcoming.value = movieUpcomingResponse.body()!!.results
                } else {
                    //error.value = context.getString(R.string.errorSearch)
                }
            }catch (exception: Exception){
                //error.value = context.getString(R.string.errorNetwork)
            }

        }
    }

}