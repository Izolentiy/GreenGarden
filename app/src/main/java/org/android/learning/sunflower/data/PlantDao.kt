package org.android.learning.sunflower.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {

    @Query("select * from plants order by name")
    fun getPlants(): Flow<List<Plant>>

    @Query("select * from plants where id = :plantId")
    fun getPlant(plantId: String): Flow<Plant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlants(plants: List<Plant>)

}