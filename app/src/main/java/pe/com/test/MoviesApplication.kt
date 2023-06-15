package pe.com.test

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import pe.com.test.repository.MoviesRepository
import pe.com.test.repository.MoviesRoomDatabase

class MoviesApplication: Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { MoviesRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { MoviesRepository(database.moviesDAO()) }
}