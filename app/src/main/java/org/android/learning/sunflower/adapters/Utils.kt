package org.android.learning.sunflower.adapters

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import okio.IOException
import org.android.learning.sunflower.R
import retrofit2.HttpException
import java.util.concurrent.TimeoutException

class ErrorTarget(
    val messageTarget: TextView,
    val detailTarget: TextView,
    val actionTarget: View,
    val context: Context
)

fun configureErrorTarget(
    errorTarget: ErrorTarget,
    message: String, detail: String,
    showAction: Boolean = true
) = with(errorTarget) {
    messageTarget.text = message
    detailTarget.text = detail
    actionTarget.isVisible = showAction
}

fun handleError(
    error: Throwable, errorTarget: ErrorTarget
) = with(errorTarget.context) {
    when (error) {
        is IOException -> configureErrorTarget(
            errorTarget = errorTarget,
            message = getString(R.string.message_no_internet),
            detail = getString(R.string.detail_no_internet)
        )
        is HttpException -> {
            when (error.code()) {
                401 -> configureErrorTarget(
                    errorTarget = errorTarget,
                    message = getString(R.string.message_401),
                    detail = getString(R.string.detail_401)
                )
                in (400 until 500) -> configureErrorTarget(
                    errorTarget = errorTarget,
                    message = getString(R.string.message_400_500),
                    detail = getString(R.string.detail_400_500, error.code(), error.message())
                )
                in (500 until 600) -> configureErrorTarget(
                    errorTarget = errorTarget,
                    message = getString(R.string.message_500_600),
                    detail = getString(R.string.detail_500_600, error.code(), error.message()),
                    showAction = false
                )
                else -> configureErrorTarget(
                    errorTarget = errorTarget,
                    message = getString(R.string.message_unexpected),
                    detail = error.message()
                )
            }
        }
        is TimeoutException -> {
            Log.e("_TAG", "handleError: ${error.message}")
            Unit
        }
        else -> configureErrorTarget(
            errorTarget = errorTarget,
            message = getString(R.string.message_unknown),
            detail = getString(R.string.detail_unknown)
        )
    }

}