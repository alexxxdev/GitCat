package com.github.alexxxdev.gitcat.common

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Priority
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class DefaultGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDefaultRequestOptions(RequestOptions()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .dontAnimate()
                .disallowHardwareConfig()
        )
    }

    override fun isManifestParsingEnabled(): Boolean = false
}

val defaultOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)