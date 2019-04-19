package me.ixidi.riotapi.call.summoner.endpoint

import khttp.get
import khttp.responses.Response
import me.ixidi.riotapi.call.Endpoint
import me.ixidi.riotapi.call.summoner.dto.Summoner

internal class SummonerByAccount(private val accountId: String): Endpoint<Summoner>(Summoner::class.java) {

    override val path: String = "/lol/summoner/v4/summoners/by-account/"

    override suspend fun call(link: String, parameters: Map<String, String>): Response {
        return get(link + accountId, params = parameters)
    }

}