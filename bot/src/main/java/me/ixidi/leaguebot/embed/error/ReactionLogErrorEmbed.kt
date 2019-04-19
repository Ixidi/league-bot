package me.ixidi.leaguebot.embed.error

import me.ixidi.leaguebot.embed.FooterHandler
import me.ixidi.leaguebot.embed.error.ErrorEmbed
import me.ixidi.leaguebot.util.extension.postOnPastebin
import net.dv8tion.jda.core.entities.MessageChannel
import net.dv8tion.jda.core.entities.MessageEmbed
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent
import java.io.PrintWriter
import java.io.StringWriter

class ReactionLogErrorEmbed(
        channel: MessageChannel,
        title: String,
        footerHandler: FooterHandler,
        private val event: PrivateMessageReactionAddEvent,
        private val throwable: Throwable
): ErrorEmbed(
        channel,
        title,
        footerHandler,
        null
) {

    override fun createFields(): List<MessageEmbed.Field> {
        val writer = StringWriter()
        throwable.printStackTrace(PrintWriter(writer))
        return listOf(
                field("Channel", "PW"),
                field("LeagueUser", event.user.asMention),
                field("Emoji", event.reactionEmote.name),
                field("Exception", "```$throwable```"),
                field("Full stack trace", "[Link to pastebin](${writer.toString().postOnPastebin("Error ${System.currentTimeMillis()}")})")
        )
    }

}