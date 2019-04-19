package me.ixidi.leaguebot.command.game

import me.ixidi.leaguebot.LeagueBot
import me.ixidi.leaguebot.command.CommandEvent
import me.ixidi.leaguebot.command.GuildCommand
import me.ixidi.leaguebot.user.LeagueUserManager
import me.ixidi.leaguebot.util.BotException
import me.ixidi.leaguebot.util.extension.errorEmbed
import me.ixidi.leaguebot.util.extension.summonerEmbed
import me.ixidi.riotapi.Region
import me.ixidi.riotapi.call.Status

class SummonerCommand: GuildCommand() {

    override suspend fun executeGuildCommand(commandEvent: CommandEvent) {
        val event = commandEvent.event
        val args = commandEvent.args
        val leagueUser = LeagueUserManager[event.author]
        val lang = leagueUser.language
        val channel = event.textChannel
        if (args.size < 2) {
            val summoner = lang["word.summoner"]
            val region = lang["word.region"]
            val langUsage = lang["correctUsage", "usage" to "!summoner $summoner $region"]
            channel.errorEmbed(lang["error"], langUsage, commandEvent)
            return
        }

        val region = Region.match(args[0])
        if (region == null) {
            channel.errorEmbed(lang["error"], lang["unknownRegion"], commandEvent)
            return
        }

        val summonerName = args.subList(1, args.size).joinToString(" ")
        val summonerResponse = LeagueBot.riotApi.summonerByName(region, summonerName)

        when (summonerResponse.status) {
            Status.OK -> {
                val summoner = summonerResponse.body!!

                val leagueResponse = LeagueBot.riotApi.leaguePositionBySummoner(region, summoner.id)
                if (leagueResponse.status != Status.OK) {
                    throw BotException("League response status is ${summonerResponse.status}.")
                }
                channel.summonerEmbed(region, lang, summoner, leagueResponse.body!!, commandEvent)
            }
            Status.DATA_NOT_FOUND -> {
                channel.errorEmbed(lang["error"], lang["unknownSummoner"], commandEvent)
            }
            else -> {
                throw BotException("Summoner response status is ${summonerResponse.status}.")
            }
        }
    }

}