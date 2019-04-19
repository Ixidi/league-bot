package me.ixidi.leaguebot.data

import me.ixidi.leaguebot.LeagueBot
import java.awt.Color

object Colors {

    lateinit var info: Color
        private set
    lateinit var error: Color
        private set
    lateinit var warn: Color
        private set
    lateinit var success: Color
        private set

    fun load() {
        info = color("color.info")
        error = color("color.error")
        warn = color("color.warn")
        success = color("color.success")
    }

    private fun color(configKey: String): Color {
        return Color.decode(LeagueBot.config.getString(configKey))
    }

}