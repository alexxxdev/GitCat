package com.github.alexxxdev.gitcat.ext

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import androidx.core.content.ContextCompat

fun Resources.vectorDrawableToBitmap(context: Context, drawableId: Int): Bitmap {
    val d = this.getDrawable(drawableId)
    if (d is VectorDrawable) {
        ContextCompat.getDrawable(context, drawableId)?.let { drawable ->
            val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }
    }

    val bit = BitmapFactory.decodeResource(context.resources, drawableId)
    return bit.copy(Bitmap.Config.ARGB_8888, true)
}