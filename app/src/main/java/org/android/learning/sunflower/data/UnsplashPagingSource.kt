package org.android.learning.sunflower.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.android.learning.sunflower.network.UNSPLASH_STARTING_PAGE_INDEX
import org.android.learning.sunflower.network.UnsplashService
import retrofit2.HttpException

class UnsplashPagingSource(
    private val service: UnsplashService,
    private val query: String
) : PagingSource<Int, UnsplashPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val page = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        val response = service.searchPhotos(query, page, params.loadSize)
        return if (response.isSuccessful) {
            val responseBody = response.body()!!
            val photos = responseBody.results
            LoadResult.Page(
                data = photos,
                prevKey = if (page == UNSPLASH_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == responseBody.totalPages) null else page + 1
            )
        } else LoadResult.Error(HttpException(response))
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? = state.anchorPosition

}