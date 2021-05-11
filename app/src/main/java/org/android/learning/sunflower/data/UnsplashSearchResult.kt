package org.android.learning.sunflower.data

import com.google.gson.annotations.SerializedName

data class UnsplashSearchResult(
    @SerializedName("results") val results: List<UnsplashPhoto>,
    @SerializedName("total_pages") val totalPages: Int
)