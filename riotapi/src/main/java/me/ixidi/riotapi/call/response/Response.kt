package me.ixidi.riotapi.call.response

import me.ixidi.riotapi.call.Status

interface Response<T> {

    val status: Status
    val body: T?

}