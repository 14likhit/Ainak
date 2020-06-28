package com.example.ainak.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Utility class.
 */
class Utils {

    companion object {

        fun pxToDp(context: Context, px: Float): Float {
            return px / context.resources.displayMetrics.density
        }

        fun dpToPx(context: Context, dp: Float): Float {
            return dp * context.resources.displayMetrics.density
        }

        fun loadImage(
            context: Context?,
            uri: String?,
            placeholder: Int,
            error: Int,
            imageView: ImageView?
        ) {
            Glide.with(context!!)
                .load(uri)
                .placeholder(placeholder)
                .error(error)
                .centerCrop()
                .into(imageView!!)
        }

    }

}