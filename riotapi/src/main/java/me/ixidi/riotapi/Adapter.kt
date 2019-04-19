package me.ixidi.riotapi

import com.squareup.moshi.JsonAdapter
import java.lang.reflect.Type

internal open class Adapter<T>(private val type: Type) {

    private val adapter: JsonAdapter<T> = RiotApi.moshi.adapter(type)

    fun fromJson(json: String): T {
        return adapter.fromJson(json) ?: throw NullPointerException("Instance cannot be null!")
    }

}