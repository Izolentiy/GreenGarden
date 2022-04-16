package org.android.learning.sunflower.adapters

import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import org.android.learning.sunflower.R

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Log.d("_TAG", "bindImageFromUrl: loading $imageUrl")
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(ColorDrawable(view.context.getColor(R.color.white)))
            .into(view)
    }
}

@BindingAdapter("imageFromAsset")
fun bindImageFromAsset(view: ImageView, imageName: String?) {
    if (!imageName.isNullOrEmpty()) {
        Log.d("_TAG", "bindImageFromAsset: loading $imageName")
        Glide.with(view.context)
            .load(Uri.parse("file:///android_asset/$imageName"))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(ColorDrawable(view.context.getColor(R.color.white)))
            .into(view)
    }
}

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) View.GONE else View.VISIBLE
}

@BindingAdapter("isPlantLayoutGone")
fun bindIsPlantLayoutGone(view: ConstraintLayout, isGone: Boolean?) {
    view.visibility = if (isGone == null || isGone) View.GONE
    else View.VISIBLE
}

@BindingAdapter("renderHtml")
fun bindRenderHtml(textView: TextView, description: String?) {
    textView.movementMethod = LinkMovementMethod.getInstance()
    textView.text =
        if (description == null) ""
        else HtmlCompat.fromHtml(description, FROM_HTML_MODE_COMPACT)
}

@BindingAdapter("wateringTextGarden")
fun bindWateringTextGarden(textView: TextView, wateringInterval: Int?) {
    textView.text = textView.resources
        .getString(R.string.watering_text_garden_format, wateringInterval)
}

@BindingAdapter("wateringText")
fun bindWateringText(textView: TextView, wateringInterval: Int?) {
    textView.text = textView.resources.getString(R.string.watering_text_format, wateringInterval)
}