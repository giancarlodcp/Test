package pe.com.test.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.com.test.models.MoviePopular
import pe.com.test.models.MovieUpcoming

@Database(entities = [MoviePopular::class, MovieUpcoming::class], version = 1, exportSchema = false)
abstract class MoviesRoomDatabase : RoomDatabase() {

    abstract fun moviesDAO(): MoviesDAO

    companion object {
        @Volatile
        private var INSTANCE: MoviesRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): MoviesRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesRoomDatabase::class.java,
                    "movies_database"
                )
                    //.addCallback(MoviesRoomDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
        private class MoviesRoomDatabaseCallback(
            private val scope: CoroutineScope
        ) : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.moviesDAO())
                    }
                }
            }
        }

        /**
         * Just for testing purposes.
         */
        suspend fun populateDatabase(moviesDAO: MoviesDAO) {
            //moviesDAO.deleteAll()
            //moviesDAO.insert(movie)
        }
    }
}