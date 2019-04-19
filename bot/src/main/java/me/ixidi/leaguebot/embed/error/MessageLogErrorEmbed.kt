package me.ixidi.leaguebot.embed.error

import me.ixidi.leaguebot.embed.FooterHandler
import me.ixidi.leaguebot.embed.error.ErrorEmbed
import me.ixidi.leaguebot.util.extension.postOnPastebin
import net.dv8tion.jda.core.entities.MessageChannel
import net.dv8tion.jda.core.entities.MessageEmbed
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import java.io.PrintWriter
import java.io.StringWriter

class MessageLogErrorEmbed(
        channel: MessageChannel,
        title: String,
        footerHandler: FooterHandler,
        private val event: MessageReceivedEvent,
        private val throwable: Throwable
): ErrorEmbed(
        channel,
        title,
        footerHandler,
        null
) {

    override fun createFields(): List<MessageEmbed.Field> {
        val channel = if (event.channelType.isGuild) event.textChannel.asMention else "PM"
        val writer = StringWriter()
        throwable.printStackTrace(PrintWriter(writer))
        return listOf(
                field("Channel", channel),
                field("LeagueUser", event.author.asMention),
                field("Message", "`${event.message.contentRaw}`"),
                field("Exception", "```$throwable```"),
                field("Full stack trace", "[Link to pastebin](${writer.toString().postOnPastebin("Error ${System.currentTimeMillis()}")})")
        )
    }

}