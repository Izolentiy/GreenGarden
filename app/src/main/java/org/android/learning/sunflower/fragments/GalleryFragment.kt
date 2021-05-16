package org.android.learning.sunflower.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.android.learning.sunflower.adapters.PhotoAdapter
import org.android.learning.sunflower.databinding.FragmentGalleryBinding
import org.android.learning.sunflower.viewmodels.GalleryViewModel

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
        return binding.root
    }

    private fun search(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchPictures(query).collectLatest { photoAdapter.submitData(it) }
        }
    }
}