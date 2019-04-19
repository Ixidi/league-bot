package me.ixidi.leaguebot.embed.info

import me.ixidi.leaguebot.data.Colors
import me.ixidi.leaguebot.embed.BaseEmbed
import me.ixidi.leaguebot.embed.FooterHandler
import net.dv8tion.jda.core.entities.MessageChannel
import net.dv8tion.jda.core.entities.MessageEmbed

open class InformationEmbed(
        channel: MessageChannel,
        title: String,
        footerHandler: FooterHandler,
        description: String?,
        titlePrefix: String = ":information_source:",
        imageUrl: String? = null,
        thumbnailUrl: String? = null
): BaseEmbed(
        channel,
        "$titlePrefix $title",
        Colors.info,
        description,
        imageUrl,
        thumbnailUrl,
        footerHandler.footer()
) {

    override fun createFields(): List<MessageEmbed.Field> {
        return emptyList()
    }

}