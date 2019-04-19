package me.ixidi.riotapi.call.league.dto

data class MiniSeries(
        val progress: String,
        val losses: Int,
        val target: Int,
        val wins: Int
)