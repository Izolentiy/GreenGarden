package org.android.learning.sunflower.repositories

import org.android.learning.sunflower.data.GardenPlant
import org.android.learning.sunflower.data.GardenPlantDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GardenPlantRepository @Inject constructor(
    private val gardenPlantDao: GardenPlantDao
) {

    fun getPlantedGardens() = gardenPlantDao.getPlantedGardens()

    fun isPlanted(plantId: String) = gardenPlantDao.isPlanted(plantId)

    suspend fun createGardenPlant(plantId: String) {
        gardenPlantDao.insertGardenPlant(GardenPlant(plantId))
    }

    suspend fun removeGardenPlant(gardenPlant: GardenPlant) {
        gardenPlantDao.deleteGardenPlant(gardenPlant)
    }

}