package org.android.learning.sunflower.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.android.learning.sunflower.adapters.ErrorTarget
import org.android.learning.sunflower.adapters.StateAdapter
import org.android.learning.sunflower.adapters.handleError
import org.android.learning.sunflower.databinding.FragmentGalleryBinding
import org.android.learning.sunflower.viewmodels.GalleryViewModel

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private val args: GalleryFragmentArgs by navArgs()
    private val viewModel: GalleryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentGalleryBinding.inflate(inflater, container, false)
        binding.apply {
            photoList.adapter = viewModel.photoAdapter.withLoadStateFooter(
                footer = StateAdapter(photoList) { viewModel.photoAdapter.retry() }
            )
            viewModel.searchPictures(args.plantName)

            toolbarGallery.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            val lifecycleAwareFlow = viewModel.photoAdapter.loadStateFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            lifecycleAwareFlow.collectLatest { states ->
                val isError = states.refresh is LoadState.Error
                val adapterIsEmpty = viewModel.photoAdapter.itemCount == 0
                binding.layoutError.isVisible = isError && adapterIsEmpty
                if (binding.layoutError.isVisible) {
                    val error = (states.refresh as LoadState.Error).error
                    val errorTarget = ErrorTarget(
                        binding.textViewErrorMessage,
                        binding.textViewErrorDetail,
                        binding.buttonRetry,
                        requireContext()
                    )
                    with(binding.buttonRetry) {
                        if (!hasOnClickListeners())
                            setOnClickListener { viewModel.searchPictures(args.plantName) }
                    }
                    handleError(error, errorTarget)
                }
            }
        }

        return binding.root
    }

}