package me.ixidi.leaguebot.embed

import net.dv8tion.jda.core.EmbedBuilder
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.entities.MessageChannel
import net.dv8tion.jda.core.entities.MessageEmbed
import net.dv8tion.jda.core.entities.TextChannel
import java.awt.Color

abstract class Embed(
        private val channel: MessageChannel,
        title: String,
        color: Color,
        author: String?,
        authorUrl: String?,
        authorIconUrl: String?,
        description: String?,
        footer: String?,
        footerIconUrl: String?,
        imageUrl: String?,
        thumbnailUrl: String?
) {

    private val message: MessageEmbed by lazy {
        val builder = EmbedBuilder()
                .setTitle(title)
                .setColor(color)
                .setAuthor(author, authorUrl, authorIconUrl)
                .setDescription(description)
                .setFooter(footer, footerIconUrl)
                .setImage(imageUrl)
                .setThumbnail(thumbnailUrl)
        createFields().forEach {
            builder.addField(it)
        }
        builder.build()
    }

    protected abstract fun createFields(): List<MessageEmbed.Field>

    fun send(): Message {
        return channel.sendMessage(message).complete()
    }

    protected fun field(name: String, value: String, inline: Boolean = true): MessageEmbed.Field {
        return MessageEmbed.Field(name, value, inline)
    }

}