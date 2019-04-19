package me.ixidi.leaguebot

import me.ixidi.leaguebot.command.CommandManager
import me.ixidi.leaguebot.command.game.SummonerCommand
import me.ixidi.leaguebot.command.test.TestExceptionCommand
import me.ixidi.leaguebot.data.Colors
import me.ixidi.leaguebot.data.message.MessageManager
import me.ixidi.leaguebot.data.yaml.YamlFile
import me.ixidi.leaguebot.listener.ReadyListener
import me.ixidi.leaguebot.util.BotException
import me.ixidi.leaguebot.util.Enableable
import me.ixidi.riotapi.RiotApi
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.core.entities.Guild
import net.dv8tion.jda.core.entities.Role
import net.dv8tion.jda.core.entities.TextChannel
import java.io.File

object LeagueBot: Enableable() {

    lateinit var config: YamlFile
        private set

    lateinit var jda: JDA
        private set

    lateinit var guild: Guild

    lateinit var botChannel: TextChannel

    lateinit var adminRole: Role

    lateinit var riotApi: RiotApi
        private set

    var debug: Boolean = false
        private set

    override fun onEnable() {
        //val defaultFile = File(LeagueBot::class.java.getResource("config.yml").toURI())
        val configFile = File("config.yml")
        config = YamlFile(configFile/*, defaultFile*/)
        config.load()

        MessageManager.loadAll()

        Colors.load()

        debug = config.getBoolean("debug")!!

        riotApi = RiotApi(config.getString("riotApiCode")!!)

        CommandManager.add(
                "test" to TestExceptionCommand(),
                "summoner" to SummonerCommand()
        )

        val token = config.getString("botToken")!!
        if (token == "TOKEN") throw BotException("Insert bot token.")
        val start = System.currentTimeMillis()
        jda = JDABuilder(AccountType.BOT)
                .setToken(token)
                .setAutoReconnect(true)
                .addEventListener(ReadyListener(start))
                .build()
    }

}

fun main() {
    LeagueBot.enable()
}