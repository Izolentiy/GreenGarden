package org.android.learning.sunflower.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.android.learning.sunflower.BuildConfig
import org.android.learning.sunflower.repositories.GardenPlantRepository
import org.android.learning.sunflower.repositories.PlantRepository
import javax.inject.Inject

@HiltViewModel
class PlantDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    plantRepository: PlantRepository,
    private val gardenPlantRepository: GardenPlantRepository
) : ViewModel() {

    val plantId: String = savedStateHandle.get<String>("plantId") ?: ""
    val isPlanted = gardenPlantRepository.isPlanted(plantId).asLiveData()
    val plant = plantRepository.getPlant(plantId).asLiveData()
    val gardenPlant = gardenPlantRepository.getGardenPlant(plantId).asLiveData()

    fun addPlantToGarden() {
        viewModelScope.launch(Dispatchers.IO) { gardenPlantRepository.createGardenPlant(plantId) }
    }

    fun removePlantFromGarden() {
        viewModelScope.launch(Dispatchers.IO) { gardenPlantRepository.removeGardenPlant(plantId) }
    }

    fun waterPlant() = viewModelScope.launch(Dispatchers.IO) {
        val plant = checkNotNull(gardenPlant.value)
        gardenPlantRepository.waterPlant(plant)
    }

    fun hasValidUnsplashKey() = BuildConfig.UNSPLASH_API_KEY != "null"

}