package org.android.learning.sunflower.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.android.learning.sunflower.di.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Plant::class, GardenPlant::class], version = 1, exportSchema = false)
@TypeConverters(CalendarConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gardenPlantDao(): GardenPlantDao
    abstract fun plantDao(): PlantDao

    class Callback @Inject constructor(
        @ApplicationScope private val applicationScope: CoroutineScope,
        @ApplicationContext private val applicationContext: Context,
        private val database: Provider<AppDatabase>,
    ) : RoomDatabase.Callback() {

        /**
         * Create and pre-populate the database.
         * @see
         * <a href="https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785">
         *     7 Pro-tips for Room
         * </a>
         */
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val plantDao = database.get().plantDao()

            val inputStream = applicationContext.assets.open("plants.json")
            JsonReader(inputStream.reader()).use { jsonReader ->
                val plantType = object : TypeToken<List<Plant>>() {}.type
                val plantList = Gson().fromJson<List<Plant>>(jsonReader, plantType)

                applicationScope.launch { plantDao.insertPlants(plantList) }
            }
            inputStream.close()
        }
    }
}