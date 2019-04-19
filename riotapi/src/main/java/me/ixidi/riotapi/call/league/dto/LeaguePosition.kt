package me.ixidi.riotapi.call.league.dto

import me.ixidi.riotapi.call.league.data.QueueType
import me.ixidi.riotapi.call.league.data.Tier

data class LeaguePosition(
        val queueType: QueueType,
        val summonerName: String,
        val hotStreak: Boolean,
        val miniSeries: MiniSeries?,
        val wins: Int,
        val veteran: Boolean,
        val losses: Int,
        val rank: String,
        val leagueId: String,
        val inactive: Boolean,
        val freshBlood: Boolean,
        val leagueName: String,
        val position: String,
        val tier: Tier,
        val summonerId: String,
        val leaguePoints: Int
)