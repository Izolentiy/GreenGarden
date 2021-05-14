package org.android.learning.sunflower.repositories

import org.android.learning.sunflower.data.Plant
import org.android.learning.sunflower.data.PlantDao
import javax.inject.Inject

class PlantRepository @Inject constructor(
    private val plantDao: PlantDao
) {
    fun getPlants() = plantDao.getPlants()

    fun getPlant(plantId: String) = plantDao.getPlant(plantId)

    suspend fun insertPlant(plants: List<Plant>) = plantDao.insertPlants(plants)

}