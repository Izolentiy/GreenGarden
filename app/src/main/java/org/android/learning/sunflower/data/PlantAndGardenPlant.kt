package org.android.learning.sunflower.data

import androidx.room.Embedded
import androidx.room.Relation

data class PlantAndGardenPlant(
    // TODO("Think about class name, as I can see it represents one-to-many relationship")
    @Embedded
    val plant: Plant,
    @Relation(parentColumn = "id", entityColumn = "plant_id")
    val gardenPlants: List<GardenPlant> = emptyList()
)