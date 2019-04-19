package me.ixidi.leaguebot.embed.success

import me.ixidi.leaguebot.data.Colors
import me.ixidi.leaguebot.embed.BaseEmbed
import me.ixidi.leaguebot.embed.FooterHandler
import net.dv8tion.jda.core.entities.MessageChannel
import net.dv8tion.jda.core.entities.MessageEmbed

open class SuccessEmbed(
        channel: MessageChannel,
        title: String,
        footerHandler: FooterHandler,
        description: String?,
        titlePrefix: String = ":white_check_mark:",
        imageUrl: String? = null,
        thumbnailUrl: String? = null
): BaseEmbed(
        channel,
        "$titlePrefix $title",
        Colors.success,
        description,
        imageUrl,
        thumbnailUrl,
        footerHandler.footer()
) {

    override fun createFields(): List<MessageEmbed.Field> {
        return emptyList()
    }

}