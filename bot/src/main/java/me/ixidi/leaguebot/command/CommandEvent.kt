package me.ixidi.leaguebot.command

import me.ixidi.leaguebot.embed.FooterHandler
import net.dv8tion.jda.core.events.message.MessageReceivedEvent

data class CommandEvent(
        val event: MessageReceivedEvent,
        val executionTime: Long,
        val args: List<String>
): FooterHandler {

    override fun footer(): String {
        var text = event.message.contentRaw
        if (text.length > 30) {
           text = "${text.substring(0..30)}..."
        }
        return "${event.author.name} `$text`"
    }

}