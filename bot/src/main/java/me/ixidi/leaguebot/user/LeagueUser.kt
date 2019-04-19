package me.ixidi.leaguebot.user

import me.ixidi.leaguebot.data.Language

data class LeagueUser(
     val discordId: Long,
     var language: Language = Language.POLISH
)