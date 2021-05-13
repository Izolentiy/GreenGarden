package org.android.learning.sunflower.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import org.android.learning.sunflower.adapters.GardenPlantAdapter
import org.android.learning.sunflower.databinding.FragmentGardenBinding

@AndroidEntryPoint
class GardenFragment : Fragment() {

    // TODO: Implement view model

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentGardenBinding.inflate(inflater, container, false)
        val gardenPlantsAdapter = GardenPlantAdapter()
        binding.apply {
            recyclerViewGardenPlants.adapter = gardenPlantsAdapter
            // TODO: Implement subscription and result handling
        }
        return binding.root
    }

    private fun handleResult() {}

}