package org.android.learning.sunflower.repositories

import org.android.learning.sunflower.data.GardenPlant
import org.android.learning.sunflower.data.GardenPlantDao
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GardenPlantRepository @Inject constructor(
    private val gardenPlantDao: GardenPlantDao
) {

    fun getPlantedGardens() = gardenPlantDao.getPlantedGardens()

    fun isPlanted(plantId: String) = gardenPlantDao.isPlanted(plantId)

    fun getGardenPlant(plantId: String) = gardenPlantDao.getGardenPlant(plantId)

    suspend fun waterPlant(gardenPlant: GardenPlant) {
        // Update method doesn't work for some reason TODO: Investigate later
        // gardenPlantDao.updateGardenPlant(gardenPlant.copy(lastWateringDate = currentDate))
        with(gardenPlantDao) {
            removeGardenPlant(gardenPlant.plantId)
            insertGardenPlant(gardenPlant.copy(lastWateringDate = Calendar.getInstance()))
        }
    }

    suspend fun createGardenPlant(plantId: String) {
        gardenPlantDao.insertGardenPlant(GardenPlant(plantId))
    }

    suspend fun removeGardenPlant(plantId: String) {
        gardenPlantDao.removeGardenPlant(plantId)
    }

}