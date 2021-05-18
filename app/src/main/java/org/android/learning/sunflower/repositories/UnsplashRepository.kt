package org.android.learning.sunflower.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.android.learning.sunflower.data.UnsplashPagingSource
import org.android.learning.sunflower.data.UnsplashPhoto
import org.android.learning.sunflower.network.UNSPLASH_PAGE_SIZE
import org.android.learning.sunflower.network.UnsplashService
import javax.inject.Inject

class UnsplashRepository @Inject constructor(
    private val service: UnsplashService
) {

    fun getSearchResultStream(query: String): Flow<PagingData<UnsplashPhoto>> = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = UNSPLASH_PAGE_SIZE),
        pagingSourceFactory = { UnsplashPagingSource(service, query) }
    ).flow

}