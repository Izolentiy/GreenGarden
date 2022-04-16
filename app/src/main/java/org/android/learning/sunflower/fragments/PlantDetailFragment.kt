package org.android.learning.sunflower.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.android.learning.sunflower.R
import org.android.learning.sunflower.databinding.FragmentPlantDetailBinding
import org.android.learning.sunflower.viewmodels.DATE_FORMAT
import org.android.learning.sunflower.viewmodels.PlantDetailViewModel

@AndroidEntryPoint
class PlantDetailFragment : Fragment(), DeletePlantDialog.Listener {

    private val plantDetailViewModel: PlantDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPlantDetailBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = plantDetailViewModel
            lifecycleOwner = viewLifecycleOwner

            plantDetailViewModel.isPlanted.observe(viewLifecycleOwner) { planted ->
                fabPlantDetail.setImageResource(
                    if (planted) R.drawable.ic_delete
                    else R.drawable.ic_plus
                )
            }
            with(plantDetailViewModel) {
                gardenPlant.observe(viewLifecycleOwner) {
                    if (it != null) {
                        labelLastWaterDate.text = DATE_FORMAT.format(it.lastWateringDate.time)
                        labelPlantDate.text = DATE_FORMAT.format(it.plantDate.time)
                    }
                }
                fabPlantDetail.setOnClickListener {
                    if (isPlanted.value == true) {
                        DeletePlantDialog()
                            .show(childFragmentManager, DeletePlantDialog.TAG)
                    } else {
                        addPlantToGarden()
                        val message = getString(R.string.added_plant_to_garden)
                        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show()
                    }
                }
            }

            imageViewGallery.setOnClickListener { navigateToGallery() }
            imageViewWater.setOnClickListener { plantDetailViewModel.waterPlant() }

            // Settings up toolbar font
            val typeface = ResourcesCompat.getFont(requireContext(), R.font.gilroy_medium)
            toolbarDetailLayout.apply {
                setCollapsedTitleTypeface(typeface)
                setExpandedTitleTypeface(typeface)
            }


            toolbarDetail.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }

        return binding.root
    }

    override fun onPositiveDialog() {
        plantDetailViewModel.removePlantFromGarden()
        val message = getString(R.string.removed_plant_from_garden)
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }

    private fun navigateToGallery() {
        plantDetailViewModel.plant.value?.let { plant ->
            val direction = PlantDetailFragmentDirections
                .actionPlantDetailFragmentToGalleryFragment(plant.name)
            findNavController().navigate(direction)
        }
    }

}