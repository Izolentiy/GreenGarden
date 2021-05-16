package org.android.learning.sunflower.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import org.android.learning.sunflower.R
import org.android.learning.sunflower.data.Plant
import org.android.learning.sunflower.databinding.FragmentPlantDetailBinding
import org.android.learning.sunflower.fragments.PlantDetailFragment.AddPlantCallback
import org.android.learning.sunflower.viewmodels.PlantDetailViewModel

@AndroidEntryPoint
class PlantDetailFragment : Fragment() {

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
            addPlantCallback = AddPlantCallback { plant ->
                plant?.let {
                    hideAppbarFab(fabAddToGarden)
                    plantDetailViewModel.addPlantToGarden()

                    val message = getString(R.string.added_plant_to_garden)
                    Snackbar.make(root, message, Snackbar.LENGTH_LONG).show()
                }
            }

            imageViewGallery.setOnClickListener { navigateToGallery() }

            var isToolbarShown = false
            // Scroll change listener begins at Y = 0 when image is fully collapsed
            scrollViewPlantDetail.setOnScrollChangeListener { _, _, scrollY, _, _ ->
                // User scrolled past image to height of toolbar and the title text is
                // underneath the toolbar, so the toolbar should be shown
                val shouldShowToolbar = scrollY > toolbarDetail.height

                // The new state of the toolbar differs from the previous one;
                // update appbar and toolbar attributes
                if (isToolbarShown != shouldShowToolbar) {
                    isToolbarShown = shouldShowToolbar

                    // Use shadow animator to add elevation if toolbar is shown
                    appbarDetail.isActivated = shouldShowToolbar
                    // Show the plant name in toolbar
                    toolbarDetailLayout.isTitleEnabled = shouldShowToolbar
                }
            }

            toolbarDetail.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }

            // TODO: Implement share feature
        }

        return binding.root
    }

    private fun navigateToGallery() {
        plantDetailViewModel.plant.value?.let { plant ->
            // TODO: Implement navigation to gallery fragment
        }
    }

    /**
     * FloatingActionButtons anchored to AppBarLayouts
     * have their visibility controlled by the scroll position.
     * We want to turn this behavior off to hide the FAB when it is clicked.
     * @see
     * This is adapted from Chris Banes'
     * <a href="https://stackoverflow.com/a/41442923" target="_top">Stack Overflow answer</a>
     */
    private fun hideAppbarFab(fab: FloatingActionButton) {
        val layoutParams = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = layoutParams.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false // <- Is it necessary? TODO: Examine more about this
        fab.hide()
    }

    fun interface AddPlantCallback {
        fun addPlant(plant: Plant?)
    }

}