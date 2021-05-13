package org.android.learning.sunflower.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.learning.sunflower.data.Plant
import org.android.learning.sunflower.databinding.ListItemPlantBinding as Binding

class PlantAdapter : ListAdapter<Plant, PlantAdapter.ViewHolder>(PlantComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(Binding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(plant: Plant) {
            binding.apply {
                this.plant = plant
                executePendingBindings()
            }
        }
    }

    object PlantComparator : DiffUtil.ItemCallback<Plant>() {
        override fun areItemsTheSame(oldItem: Plant, newItem: Plant) =
            oldItem.plantId == newItem.plantId

        override fun areContentsTheSame(oldItem: Plant, newItem: Plant) =
            oldItem == newItem
    }

}
