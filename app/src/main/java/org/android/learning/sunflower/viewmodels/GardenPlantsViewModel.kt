package org.android.learning.sunflower.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import org.android.learning.sunflower.adapters.GardenPlantAdapter
import org.android.learning.sunflower.data.PlantAndGardenPlant
import org.android.learning.sunflower.repositories.GardenPlantRepository
import javax.inject.Inject

@HiltViewModel
class GardenPlantsViewModel @Inject constructor(
    gardenPlantRepository: GardenPlantRepository
) : ViewModel() {
    val gardenPlantsAdapter = GardenPlantAdapter()
    val plantAndGardenPlants: Flow<List<PlantAndGardenPlant>> =
        gardenPlantRepository.getPlantedGardens()
}