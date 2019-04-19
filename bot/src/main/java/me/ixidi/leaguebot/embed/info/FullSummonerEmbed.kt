package me.ixidi.leaguebot.embed.info

import me.ixidi.leaguebot.LeagueBot
import me.ixidi.leaguebot.data.Language
import me.ixidi.leaguebot.embed.FooterHandler
import me.ixidi.leaguebot.util.extension.avatarUrl
import me.ixidi.riotapi.Region
import me.ixidi.riotapi.call.league.data.QueueType
import me.ixidi.riotapi.call.league.dto.LeaguePosition
import me.ixidi.riotapi.call.summoner.dto.Summoner
import net.dv8tion.jda.core.entities.MessageChannel
import net.dv8tion.jda.core.entities.MessageEmbed

class FullSummonerEmbed(
        channel: MessageChannel,
        footerHandler: FooterHandler,
        private val region: Region,
        private val lang: Language,
        private val summoner: Summoner,
        private val leaguePositions: List<LeaguePosition>
): InformationEmbed(
        channel,
        lang["summonerInfo"],
        footerHandler,
        "",
        thumbnailUrl = summoner.name.avatarUrl(region)
) {

    override fun createFields(): List<MessageEmbed.Field> {
        val list = arrayListOf(
                field(lang["word.summoner"], summoner.name),
                field(lang["word.region"], region.fullName),
                field("Lvl", summoner.summonerLevel.toString())
        )
        leaguePositions.forEach {
            val name = when (it.queueType) {
                QueueType.RANKED_SOLO_5X5 -> lang["queue.solo"]
                QueueType.RANKED_FLEX_SR -> lang["queue.flexsr"]
                QueueType.RANKED_FLEX_TT -> lang["queue.flextt"]
            }
            list.add(field(name, "${it.tier.name.toLowerCase().capitalize()} ${it.rank}"))
        }
        return list
    }
}