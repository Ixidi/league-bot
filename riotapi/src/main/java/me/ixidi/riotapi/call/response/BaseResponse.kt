package me.ixidi.riotapi.call.response

import me.ixidi.riotapi.call.Status

internal class BaseResponse<T>(override val status: Status, override val body: T?) : Response<T>