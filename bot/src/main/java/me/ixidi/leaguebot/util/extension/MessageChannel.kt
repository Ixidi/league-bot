package me.ixidi.leaguebot.util.extension

import me.ixidi.leaguebot.data.Language
import me.ixidi.leaguebot.embed.BaseFooter
import me.ixidi.leaguebot.embed.FooterHandler
import me.ixidi.leaguebot.embed.error.ErrorEmbed
import me.ixidi.leaguebot.embed.info.InformationEmbed
import me.ixidi.leaguebot.embed.error.MessageLogErrorEmbed
import me.ixidi.leaguebot.embed.error.ReactionLogErrorEmbed
import me.ixidi.leaguebot.embed.info.FullSummonerEmbed
import me.ixidi.leaguebot.embed.success.SuccessEmbed
import me.ixidi.riotapi.Region
import me.ixidi.riotapi.call.league.dto.LeaguePosition
import me.ixidi.riotapi.call.summoner.dto.Summoner
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.entities.MessageChannel
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent

private val emptyFooter = BaseFooter("")

fun MessageChannel.logInfoEmbed(text: String) {
    InformationEmbed(this, "LeagueBot information log.", emptyFooter, text).send()
}

fun MessageChannel.logErrorEmbed(event: MessageReceivedEvent, throwable: Throwable) {
    MessageLogErrorEmbed(this, "LeagueBot error log.", emptyFooter, event, throwable).send()
}

fun MessageChannel.logErrorEmbed(event: PrivateMessageReactionAddEvent, throwable: Throwable) {
    ReactionLogErrorEmbed(this, "LeagueBot error log.", emptyFooter, event, throwable).send()
}

fun MessageChannel.errorEmbed(title: String, text: String, footerHandler: FooterHandler = emptyFooter) {
    ErrorEmbed(this, title, footerHandler, text).send()
}

fun MessageChannel.summonerEmbed(region: Region, language: Language, summoner: Summoner, leaguePositions: List<LeaguePosition>, footerHandler: FooterHandler = emptyFooter): Message {
    return FullSummonerEmbed(this, footerHandler, region, language, summoner, leaguePositions).send()
}

fun MessageChannel.infoEmbed(title: String, text: String, footerHandler: FooterHandler = emptyFooter): Message {
    return InformationEmbed(this, title, footerHandler, text).send()
}

fun MessageChannel.successEmbed(title: String, text: String, footerHandler: FooterHandler = emptyFooter): Message {
    return SuccessEmbed(this, title, footerHandler, text).send()
}