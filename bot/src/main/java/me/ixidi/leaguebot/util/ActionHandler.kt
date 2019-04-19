package me.ixidi.leaguebot.util

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.ixidi.leaguebot.LeagueBot
import me.ixidi.leaguebot.command.test.TestExceptionCommand
import me.ixidi.leaguebot.embed.FooterHandler
import me.ixidi.leaguebot.user.LeagueUserManager
import me.ixidi.leaguebot.util.extension.errorEmbed
import me.ixidi.leaguebot.util.extension.logErrorEmbed
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent

object ActionHandler {

    fun handleSuspend(event: MessageReceivedEvent, errorFooterHandler: FooterHandler = FooterHandler.EMPTY, run: suspend () -> Unit) {
        GlobalScope.launch {
            try {
                run()
            } catch (throwable: Throwable) {
                if (throwable !is TestExceptionCommand.TestException) {
                    throwable.printStackTrace()
                }
                val lang = LeagueUserManager[event.author].language
                event.channel.errorEmbed(lang["error"], lang["unexpectedError"], errorFooterHandler)
                LeagueBot.botChannel.logErrorEmbed(event, throwable)
            }
        }
    }

    fun handleSuspend(event: PrivateMessageReactionAddEvent, errorFooterHandler: FooterHandler = FooterHandler.EMPTY, run: suspend () -> Unit) {
        GlobalScope.launch {
            try {
                run()
            } catch (throwable: Throwable) {
                if (throwable !is TestExceptionCommand.TestException) {
                    throwable.printStackTrace()
                }
                val lang = LeagueUserManager[event.user].language
                event.channel.errorEmbed(lang["error"], lang["unexpectedError"], errorFooterHandler)
                LeagueBot.botChannel.logErrorEmbed(event, throwable)
            }
        }
    }

}