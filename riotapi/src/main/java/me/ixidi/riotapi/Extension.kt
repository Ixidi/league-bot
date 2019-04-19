package me.ixidi.riotapi

import java.net.URLEncoder

fun String.encode(): String {
    return URLEncoder.encode(this, "UTF-8").replace("+", "%20")
}