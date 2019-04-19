package me.ixidi.riotapi.datadragon.dto

import com.squareup.moshi.Json

data class Realms(
        @Json(name = "v")
        val version: String
)