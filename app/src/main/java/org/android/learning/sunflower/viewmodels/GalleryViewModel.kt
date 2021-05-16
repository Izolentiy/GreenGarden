package org.android.learning.sunflower.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import org.android.learning.sunflower.data.UnsplashPhoto
import org.android.learning.sunflower.data.UnsplashRepository
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: UnsplashRepository
) : ViewModel() {
    private var currentQuery: String? = null
    private var currentSearchResult: Flow<PagingData<UnsplashPhoto>>? = null

    fun searchPictures(query: String): Flow<PagingData<UnsplashPhoto>> {
        currentQuery = query
        val newResult = repository.getSearchResultStream(query).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}