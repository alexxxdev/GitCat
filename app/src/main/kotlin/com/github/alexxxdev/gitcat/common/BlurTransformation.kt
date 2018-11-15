package com.github.alexxxdev.gitcat.common

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.Resource
import java.security.MessageDigest
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.R.attr.radius
import android.R.attr.bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.FILTER_BITMAP_FLAG
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapResource
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import android.renderscript.Element.U8_4
import android.renderscript.ScriptIntrinsicBlur
import android.renderscript.Allocation
import android.renderscript.RenderScript









const val MAX_RADIUS = 25f

class BlurTransformation(private val context:Context, private var radius:Float = MAX_RADIUS):BitmapTransformation() {
    private var rs: RenderScript? = null

    init {
        rs = RenderScript.create(context)
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update("blur transformation".toByteArray())
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val blurredBitmap = toTransform.copy(Bitmap.Config.ARGB_8888, true)

        // Allocate memory for Renderscript to work with
        val input = Allocation.createFromBitmap(rs, blurredBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED)
        val output = Allocation.createTyped(rs, input.type)

        // Load up an instance of the specific script that we want to use.
        val script = ScriptIntrinsicBlur.create(rs, U8_4(rs))
        script.setInput(input)

        // Set the blur radius
        if(radius>MAX_RADIUS) radius = MAX_RADIUS
        script.setRadius(radius)

        // Start the ScriptIntrinisicBlur
        script.forEach(output)

        // Copy the output to the blurred bitmap
        output.copyTo(blurredBitmap)

        return blurredBitmap
    }

}