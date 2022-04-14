package org.android.learning.sunflower.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okio.IOException
import org.android.learning.sunflower.R
import org.android.learning.sunflower.adapters.PhotoAdapter
import org.android.learning.sunflower.databinding.FragmentGalleryBinding
import org.android.learning.sunflower.viewmodels.GalleryViewModel
import retrofit2.HttpException
import java.util.concurrent.TimeoutException

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private var searchJob: Job? = null
    private val photoAdapter = PhotoAdapter()
    private val args: GalleryFragmentArgs by navArgs()
    private val viewModel: GalleryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentGalleryBinding.inflate(inflater, container, false)
        binding.apply {
            photoList.adapter = photoAdapter
            search(args.plantName)

            toolbarGallery.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            photoAdapter.loadStateFlow.collectLatest { states ->
                val isError = states.refresh is LoadState.Error
                binding.layoutError.isVisible = isError
                if (isError) {
                    val error = (states.refresh as LoadState.Error).error
                    handleError(error, binding)
                }
            }
        }

        return binding.root
    }

    private fun search(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchPictures(query).collectLatest { photoAdapter.submitData(it) }
        }
    }

    private fun handleError(
        error: Throwable,
        binding: FragmentGalleryBinding
    ) = with(binding) {
        with(buttonRetry) {
            if (!hasOnClickListeners())
                setOnClickListener { search(args.plantName) }
        }
        when (error) {
            is IOException -> configureText(
                message = getString(R.string.message_no_internet),
                detail = getString(R.string.detail_no_internet)
            )
            is HttpException -> {
                when (error.code()) {
                    401 -> configureText(
                        message = getString(R.string.message_401),
                        detail = getString(R.string.detail_401)
                    )
                    in (400 until 500) -> configureText(
                        message = getString(R.string.message_400_500),
                        detail = getString(R.string.detail_400_500, error.code(), error.message())
                    )
                    in (500 until 600) -> configureText(
                        message = getString(R.string.message_500_600),
                        detail = getString(R.string.detail_500_600, error.code(), error.message())
                    )
                    else -> configureText(
                        message = getString(R.string.message_unexpected),
                        detail = error.message()
                    )
                }
            }
            is TimeoutException -> {
                Log.e("_TAG", "handleError: ${error.message}")
                Unit
            }
            else -> configureText(
                message = getString(R.string.message_unknown),
                detail = getString(R.string.detail_unknown)
            )
        }
    }

}