package org.android.learning.sunflower.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GardenPlantDao {

    @Query("select * from garden_plants")
    fun getGardenPlants(): Flow<List<GardenPlant>>

    @Query("select exists (select 1 from garden_plants where plant_id = :plantId limit 1)")
    fun isPlanted(plantId: String): Flow<Boolean>

    @Transaction // TODO: Examine this method later
    @Query("select * from plants where id in (select distinct(plant_id) from garden_plants)")
    fun getPlantedGardens(): Flow<List<PlantAndGardenPlant>>

    @Query("select * from garden_plants where plant_id = :plantId")
    fun getGardenPlant(plantId: String): Flow<GardenPlant?>

    @Query("delete from garden_plants where plant_id = :plantId")
    suspend fun removeGardenPlant(plantId: String)

    @Update
    suspend fun updateGardenPlant(gardenPlant: GardenPlant)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGardenPlant(gardenPlant: GardenPlant)

    @Delete
    suspend fun deleteGardenPlant(gardenPlant: GardenPlant)

}