package me.ixidi.leaguebot.util.extension

import me.ixidi.leaguebot.LeagueBot
import java.text.SimpleDateFormat
import java.util.*

private val formatter by lazy {
    SimpleDateFormat(LeagueBot.config.getString("dateFormat"))
}

fun Date.format(): String {
    return formatter.format(this)
}