package com.github.alexxxdev.gitcat.common

import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialDescriptor
import kotlinx.serialization.Serializer
import kotlinx.serialization.internal.StringDescriptor
import kotlinx.serialization.withName

@Serializer(forClass = Any::class)
class AnySerializer: KSerializer<Any> {
    override val descriptor: SerialDescriptor =
            StringDescriptor.withName("WithCustomDefault")

    override fun serialize(output: Encoder, obj: Any) {
        output.encodeString(obj.toString())
    }

    override fun deserialize(input: Decoder): Any {
        return input.decodeString()
    }
}