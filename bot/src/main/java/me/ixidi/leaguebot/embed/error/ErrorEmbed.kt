package me.ixidi.leaguebot.embed.error

import me.ixidi.leaguebot.data.Colors
import me.ixidi.leaguebot.embed.BaseEmbed
import me.ixidi.leaguebot.embed.FooterHandler
import net.dv8tion.jda.core.entities.MessageChannel
import net.dv8tion.jda.core.entities.MessageEmbed

open class ErrorEmbed(
       channel: MessageChannel,
       title: String,
       footerHandler: FooterHandler,
       description: String?,
       titlePrefix: String = ":no_entry:",
       thumbnailUrl: String? = null
): BaseEmbed(
        channel,
        "$titlePrefix $title",
        Colors.error,
        description,
        null,
        thumbnailUrl,
        footerHandler.footer()
) {

    override fun createFields(): List<MessageEmbed.Field> {
        return emptyList()
    }

}