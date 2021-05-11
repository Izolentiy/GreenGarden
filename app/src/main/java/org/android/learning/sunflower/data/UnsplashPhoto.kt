package org.android.learning.sunflower.data

import com.google.gson.annotations.SerializedName

data class UnsplashPhoto(
    @SerializedName("id") val id: String,
    @SerializedName("urls") val urls: UnsplashPhotoUrls,
    @SerializedName("user") val user: UnsplashUser
) {
    data class UnsplashPhotoUrls(
        @SerializedName("small") val small: String
    )

    data class UnsplashUser(
        @SerializedName("name") val name: String,
        @SerializedName("username") val username: String
    ) {
        // TODO("Examine this property later")
        val attributionUrl: String
            get() = "https://unsplash.com/$username?utm_source=sunflower&utm_medium=referral"
    }
}