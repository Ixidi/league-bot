package me.ixidi.leaguebot.listener

import me.ixidi.leaguebot.util.BotException
import me.ixidi.leaguebot.LeagueBot
import me.ixidi.leaguebot.command.CommandManager
import me.ixidi.leaguebot.command.test.TestExceptionCommand
import me.ixidi.leaguebot.util.extension.logInfoEmbed
import net.dv8tion.jda.core.Permission
import net.dv8tion.jda.core.entities.TextChannel
import net.dv8tion.jda.core.events.ReadyEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter
import java.awt.Color

class ReadyListener(private val start: Long): ListenerAdapter() {

    override fun onReady(event: ReadyEvent) {
        val guild = event.jda.getGuildById(LeagueBot.config.getString("leagueGuildId"))
        if (guild == null) {
            event.jda.shutdownNow()
            throw BotException("Bot is not at league server!")
        }
        LeagueBot.guild = guild

        val roleName = LeagueBot.config.getString("adminRole")
        val roles = guild.getRolesByName(roleName, false)
        var roleCreated = false
        val role  = if (roles.isEmpty()) {
            roleCreated = true
            guild.controller.createRole()
                    .setName(roleName)
                    .setColor(Color.RED)
                    .complete()
        } else {
            roles[0]
        }
        LeagueBot.adminRole = role

        val channelName = LeagueBot.config.getString("botDebugChannelName")
        val channels = guild.getTextChannelsByName(channelName, false)
        val channel = if (channels.isEmpty()) {
            val channel = guild.controller.createTextChannel(channelName)
                    .setPosition(0)
                    .addPermissionOverride(guild.publicRole, emptySet(), setOf(Permission.MESSAGE_READ))
                    .addPermissionOverride(role, setOf(Permission.MESSAGE_READ), emptySet())
                    .complete() as TextChannel
            channel.logInfoEmbed("${channel.asMention} has been created.")
            channel
        } else {
            channels[0]
        }
        LeagueBot.botChannel = channel as TextChannel

        if (roleCreated) {
            channel.logInfoEmbed("${role.asMention} has been created.")
        }

        event.jda.addEventListener(MessageListener(), ReactionListener())

        val time = System.currentTimeMillis() - start
        channel.logInfoEmbed("Bot has started in ${time}ms.")
    }
}