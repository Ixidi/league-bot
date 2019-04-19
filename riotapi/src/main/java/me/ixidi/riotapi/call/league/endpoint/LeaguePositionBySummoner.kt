package me.ixidi.riotapi.call.league.endpoint

import com.squareup.moshi.Types
import khttp.get
import khttp.responses.Response
import me.ixidi.riotapi.call.Endpoint
import me.ixidi.riotapi.call.league.dto.LeaguePosition
import me.ixidi.riotapi.call.summoner.dto.Summoner

internal class LeaguePositionBySummoner(private val summonerId: String): Endpoint<List<LeaguePosition>>(Types.newParameterizedType(List::class.java, LeaguePosition::class.java)) {

    override val path: String = "/lol/league/v4/positions/by-summoner/"

    override suspend fun call(link: String, parameters: Map<String, String>): Response {
        return get(link + summonerId, params = parameters)
    }

}