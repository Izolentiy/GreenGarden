package org.android.learning.sunflower.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.android.learning.sunflower.R
import org.android.learning.sunflower.adapters.GardenPlantAdapter
import org.android.learning.sunflower.adapters.Tab
import org.android.learning.sunflower.data.PlantAndGardenPlant
import org.android.learning.sunflower.databinding.FragmentGardenBinding
import org.android.learning.sunflower.viewmodels.GardenPlantsViewModel

@AndroidEntryPoint
class GardenFragment : Fragment() {

    private var _binding: FragmentGardenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GardenPlantsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGardenBinding.inflate(inflater, container, false)

        binding.apply {
            recyclerViewGardenPlants.adapter = viewModel.gardenPlantsAdapter
            buttonAddPlant.setOnClickListener { navigateToPlantList() }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.plantAndGardenPlants
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { list -> handleListUpdate(list) }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleListUpdate(list: List<PlantAndGardenPlant>) {
        with(binding) {
            val adapter = recyclerViewGardenPlants.adapter as GardenPlantAdapter
            hasPlantings = !list.isNullOrEmpty()
            adapter.submitList(list)
        }
    }

    private fun navigateToPlantList() {
        requireActivity().findViewById<ViewPager2>(R.id.view_pager)
            .currentItem = Tab.values().indexOf(Tab.PLANTS)
    }

}