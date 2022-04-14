package org.android.learning.sunflower.fragments

import androidx.core.view.isVisible
import org.android.learning.sunflower.databinding.FragmentGalleryBinding

fun FragmentGalleryBinding.configureText(
    message: String, detail: String,
    showAction: Boolean = true
) {
    textViewErrorMessage.text = message
    textViewErrorDetail.text = detail
    buttonRetry.isVisible = showAction
}