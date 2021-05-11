package org.android.learning.sunflower.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey @ColumnInfo(name = "id")
    val plantId: String,
    val name: String,
    val description: String,
    val growZone: Int,
    val wateringInterval: Int = 7, // Value represented in days
    val imageUrl: String = ""
) {

    fun shouldBeWatered(since: Calendar, lastWateringDate: Calendar) =
        since > lastWateringDate.apply { add(Calendar.DAY_OF_YEAR, wateringInterval) }

    override fun toString() = name
}