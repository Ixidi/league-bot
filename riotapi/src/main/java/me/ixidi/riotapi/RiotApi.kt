package me.ixidi.riotapi

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import me.ixidi.riotapi.call.league.data.QueueType
import me.ixidi.riotapi.call.league.data.Tier
import me.ixidi.riotapi.call.league.dto.LeaguePosition
import me.ixidi.riotapi.call.league.endpoint.LeaguePositionBySummoner
import me.ixidi.riotapi.call.response.Response
import me.ixidi.riotapi.call.summoner.dto.Summoner
import me.ixidi.riotapi.call.summoner.endpoint.SummonerByAccount
import me.ixidi.riotapi.call.summoner.endpoint.SummonerByName
import me.ixidi.riotapi.call.summoner.endpoint.SummonerByPUUID
import me.ixidi.riotapi.datadragon.DataDragon
import me.ixidi.riotapi.datadragon.DataDragonImpl

class RiotApi(apiKey: String) {


    internal companion object {
        val moshi: Moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .add(object {

                    @FromJson fun toTier(string: String): Tier {
                        return Tier.valueOf(string.toUpperCase())
                    }

                    @FromJson fun toQueueType(string: String): QueueType {
                        return QueueType.valueOf(string.toUpperCase())
                    }
                })
                .build()
    }

    private val parameters = HashMap<String, String>()
    val dataDragon: DataDragon

    init {
        setApiKey(apiKey)
        dataDragon = DataDragonImpl()
    }

    fun setApiKey(apiKey: String) {
        parameters["api_key"] = apiKey
    }

    suspend fun summonerByName(region: Region, name: String): Response<Summoner> {
        return SummonerByName(name).request(region, parameters)
    }

    suspend fun summonerByPuuid(region: Region, puuid: String): Response<Summoner> {
        return SummonerByPUUID(puuid).request(region, parameters)
    }

    suspend fun summonerByAccount(region: Region, accountId: String): Response<Summoner> {
        return SummonerByAccount(accountId).request(region, parameters)
    }

    suspend fun leaguePositionBySummoner(region: Region, summonerId: String): Response<List<LeaguePosition>> {
        return LeaguePositionBySummoner(summonerId).request(region, parameters)
    }

}
