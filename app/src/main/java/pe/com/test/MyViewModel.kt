package pe.com.test

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MyViewModel(application: Application) : AndroidViewModel(application) {

    val data = MutableLiveData<List<MoviePopular?>>()
    val movieUpcoming = MutableLiveData<List<MovieUpcoming?>>()
    val error = MutableLiveData<String>()

    @SuppressLint("StaticFieldLeak")
    val context = getApplication<Application>().applicationContext

    fun data() {

        viewModelScope.launch {
            val response = ApiManager.get().popularMovies("d9ae4921794c06bd0fdbd1463d274804", "1", "en-US")
            if (response.isSuccessful) {
                data.value = response.body()!!.results
            } else {
                error.value = context.getString(R.string.errorSearch)
            }

            val movieUpcomingResponse = ApiManager.get().upcomingMovies("d9ae4921794c06bd0fdbd1463d274804", "1", "en-US")
            if (response.isSuccessful) {
                movieUpcoming.value = movieUpcomingResponse.body()!!.results
            } else {
                error.value = context.getString(R.string.errorSearch)
            }
        }
    }

}