package me.ixidi.leaguebot.embed.warn

import me.ixidi.leaguebot.data.Colors
import me.ixidi.leaguebot.embed.BaseEmbed
import me.ixidi.leaguebot.embed.FooterHandler
import net.dv8tion.jda.core.entities.MessageChannel
import net.dv8tion.jda.core.entities.MessageEmbed

open class WarnEmbed(
        channel: MessageChannel,
        title: String,
        footerHandler: FooterHandler,
        description: String?,
        titlePrefix: String = ":warning:"
): BaseEmbed(
        channel,
        "$titlePrefix $title",
        Colors.warn,
        description,
        null,
        null,
        footerHandler.footer()
) {

    override fun createFields(): List<MessageEmbed.Field> {
        return emptyList()
    }

}