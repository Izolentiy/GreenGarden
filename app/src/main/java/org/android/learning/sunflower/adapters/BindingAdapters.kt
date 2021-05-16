package org.android.learning.sunflower.adapters

import android.graphics.drawable.ColorDrawable
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) View.GONE else View.VISIBLE
}

@BindingAdapter("isFabGone")
fun bindIsFabGone(view: FloatingActionButton, isGone: Boolean?) {
    if (isGone == null || isGone) view.hide()
    else view.show()
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