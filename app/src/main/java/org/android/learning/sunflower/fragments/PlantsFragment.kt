package org.android.learning.sunflower.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import org.android.learning.sunflower.adapters.PlantAdapter
import org.android.learning.sunflower.databinding.FragmentPlantListBinding
import org.android.learning.sunflower.databinding.FragmentPlantListBindingImpl

@AndroidEntryPoint
class PlantsFragment : Fragment() {

    // TODO: Implement view model

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPlantListBinding.inflate(inflater, container, false)
        val plantAdapter = PlantAdapter()
        binding.apply {
            recyclerViewPlantList.adapter = plantAdapter
            // TODO: Implement subscription and result handling
        }
        return binding.root
    }

    private fun handleResult() {}

}