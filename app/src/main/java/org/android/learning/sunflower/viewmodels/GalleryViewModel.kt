package org.android.learning.sunflower.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.android.learning.sunflower.adapters.PhotoAdapter
import org.android.learning.sunflower.repositories.UnsplashRepository
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: UnsplashRepository
) : ViewModel() {
    val photoAdapter = PhotoAdapter()

    fun searchPictures(query: String) {
        viewModelScope.launch {
            val searchResult = repository
                .getSearchResultStream(query)
                .cachedIn(viewModelScope)
            searchResult.collectLatest { photoAdapter.submitData(it) }
        }
    }
}