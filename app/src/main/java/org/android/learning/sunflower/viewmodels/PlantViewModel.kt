package org.android.learning.sunflower.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import org.android.learning.sunflower.adapters.PlantAdapter
import org.android.learning.sunflower.data.Plant
import org.android.learning.sunflower.repositories.PlantRepository
import javax.inject.Inject

@HiltViewModel
class PlantViewModel @Inject constructor(
    plantRepository: PlantRepository,
) : ViewModel() {
    val plantAdapter = PlantAdapter()
    val plants: Flow<List<Plant>> = plantRepository.getPlants()
}