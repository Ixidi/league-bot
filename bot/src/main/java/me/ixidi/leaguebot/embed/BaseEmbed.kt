package me.ixidi.leaguebot.embed

import me.ixidi.leaguebot.LeagueBot
import me.ixidi.leaguebot.util.extension.format
import net.dv8tion.jda.core.entities.MessageChannel
import net.dv8tion.jda.core.entities.TextChannel
import java.awt.Color
import java.util.*

abstract class BaseEmbed(
        channel: MessageChannel,
        title: String,
        color: Color,
        description: String?,
        imageUrl: String?,
        thumbnailUrl: String?,
        footer: String
): Embed(
        channel,
        title,
        color,
        null,
        null,
        null,
        description,
        footer,
        LeagueBot.jda.selfUser.avatarUrl,
        imageUrl,
        thumbnailUrl
)