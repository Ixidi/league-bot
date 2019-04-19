package me.ixidi.riotapi.call

import khttp.responses.Response
import me.ixidi.riotapi.Adapter
import me.ixidi.riotapi.Region
import me.ixidi.riotapi.call.response.BaseResponse
import java.lang.reflect.Type

internal abstract class Endpoint<T>(type: Type): Adapter<T>(type) {

    abstract val path: String

    suspend fun request(region: Region, parameters: Map<String, String>): me.ixidi.riotapi.call.response.Response<T> {
        val response = call(region.getLink() + path, parameters)
        val status = Status.match(response.statusCode)

        val body = if (status == Status.OK) {
            fromJson(response.text)
        } else {
            null
        }
        return BaseResponse(status, body)
    }

    protected abstract suspend fun call(link: String, parameters: Map<String, String>): Response
}