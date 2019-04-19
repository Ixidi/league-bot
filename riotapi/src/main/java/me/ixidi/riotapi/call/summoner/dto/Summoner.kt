package me.ixidi.riotapi.call.summoner.dto

data class Summoner(
        val profileIconId: Int,
        val name: String,
        val puuid: String,
        val summonerLevel: Long,
        val revisionDate: Long,
        val id: String,
        val accountId: String
)