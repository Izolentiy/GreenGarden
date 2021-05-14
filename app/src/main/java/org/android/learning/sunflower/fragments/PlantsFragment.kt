package org.android.learning.sunflower.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import org.android.learning.sunflower.adapters.PlantAdapter
import org.android.learning.sunflower.data.Plant
import org.android.learning.sunflower.databinding.FragmentPlantListBinding
import org.android.learning.sunflower.viewmodels.PlantViewModel

@AndroidEntryPoint
class PlantsFragment : Fragment() {

    private var _binding: FragmentPlantListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlantViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlantListBinding.inflate(inflater, container, false)
        /*context ?: return binding.root*/

        val plantAdapter = PlantAdapter()
        binding.recyclerViewPlantList.adapter = plantAdapter

        // Subscribe to UI updates
        lifecycleScope.launchWhenStarted {
            viewModel.plants.collect { list -> handleListUpdate(list) }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleListUpdate(list: List<Plant>) {
        with(binding) {
            val adapter = recyclerViewPlantList.adapter as PlantAdapter
            adapter.submitList(list)
        }
    }

}