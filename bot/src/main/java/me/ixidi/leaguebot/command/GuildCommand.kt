package me.ixidi.leaguebot.command

import me.ixidi.leaguebot.user.LeagueUserManager
import me.ixidi.leaguebot.util.extension.errorEmbed
import net.dv8tion.jda.core.events.message.MessageReceivedEvent

abstract class GuildCommand: Command {

    final override suspend fun execute(commandEvent: CommandEvent) {
        val event = commandEvent.event
        if (!event.channelType.isGuild) {
            val lang = LeagueUserManager.get(event.author).language
            val messageTitle = lang["usageError"]
            val message = lang["channelOnly.guild"]
            event.textChannel.errorEmbed(messageTitle, message, commandEvent)
            return
        }
        executeGuildCommand(commandEvent)
    }

    protected abstract suspend fun executeGuildCommand(commandEvent: CommandEvent)

}