package org.android.learning.sunflower.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.learning.sunflower.fragments.ViewPagerFragmentDirections
import org.android.learning.sunflower.viewmodels.PlantAndGardenPlantViewModel
import org.android.learning.sunflower.data.PlantAndGardenPlant as ListItem
import org.android.learning.sunflower.databinding.ListItemGardenPlantBinding as Binding

class GardenPlantAdapter :
    ListAdapter<ListItem, GardenPlantAdapter.ViewHolder>(GardenPlantComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(Binding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: Binding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                setClickListener { view -> viewModel?.plantId?.let { navigateTo(it, view) }}
            }
        }

        fun bind(gardenPlant: ListItem) {
            /*with(binding) {*/
            binding.apply {
                viewModel = PlantAndGardenPlantViewModel(gardenPlant)
                executePendingBindings()
            }
        }

        private fun navigateTo(plantId: String, view: View) {
            val direction = ViewPagerFragmentDirections
                .actionViewPagerFragmentToPlantDetailFragment(plantId)
            view.findNavController().navigate(direction)
        }

    }

    object GardenPlantComparator : DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem) =
            oldItem.plant.plantId == newItem.plant.plantId

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem) =
            oldItem.plant == newItem.plant
    }
}