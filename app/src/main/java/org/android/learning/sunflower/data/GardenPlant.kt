package org.android.learning.sunflower.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity(
    tableName = "garden_plants",
    foreignKeys = [ForeignKey(
        entity = Plant::class,
        parentColumns = ["id"],
        childColumns = ["plant_id"]
    )],
    // TODO("Examine indices and foreign keys more deeply")
    indices = [Index("plant_id")]
)
data class GardenPlant(
    @ColumnInfo(name = "plant_id")
    val plantId: String,
    @ColumnInfo(name = "plant_date")
    val plantDate: Calendar = Calendar.getInstance(),
    @ColumnInfo(name = "last_watering_date")
    val lastWateringDate: Calendar = Calendar.getInstance()
) {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    var gardenPlantId: Long = 0
}