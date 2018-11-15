package com.github.alexxxdev.gitcat.common

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element.U8_4
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

const val MAX_RADIUS = 25f

class BlurTransformation(private val context: Context, private var radius: Float = MAX_RADIUS) : BitmapTransformation() {
    private var rs: RenderScript? = null

    init {
        rs = RenderScript.create(context)
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update("blur transformation".toByteArray())
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val blurredBitmap = toTransform.copy(Bitmap.Config.ARGB_8888, true)
        val input = Allocation.createFromBitmap(rs, blurredBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED)
        val output = Allocation.createTyped(rs, input.type)
        val script = ScriptIntrinsicBlur.create(rs, U8_4(rs))
        script.setInput(input)
        if (radius > MAX_RADIUS) radius = MAX_RADIUS
        script.setRadius(radius)
        script.forEach(output)
        output.copyTo(blurredBitmap)
        return blurredBitmap
    }
}