package com.example.currencies.data.api

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.util.*

class UnixTimestampAdapter : TypeAdapter<Date?>() {
    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: Date?) {
        if (value == null) {
            out.nullValue()
            return
        }
        out.value(value.time / 1000)
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Date? {
        if (`in`.peek() === JsonToken.NULL) {
            `in`.nextNull()
            return null
        }
        return Date(`in`.nextLong() * 1000)
    }
}