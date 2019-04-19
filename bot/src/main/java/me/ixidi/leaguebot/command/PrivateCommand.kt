package me.ixidi.leaguebot.command

import me.ixidi.leaguebot.user.LeagueUserManager
import me.ixidi.leaguebot.util.extension.errorEmbed

abstract class PrivateCommand: Command {

    final override suspend fun execute(commandEvent: CommandEvent) {
        val event = commandEvent.event
        if (event.channelType.isGuild) {
            val lang = LeagueUserManager.get(event.author).language
            val messageTitle = lang["error"]
            val message = lang["channelOnly.private"]
            event.textChannel.errorEmbed(messageTitle, message, commandEvent)
            return
        }
        executePrivateMessage(commandEvent)
    }

    protected abstract suspend fun executePrivateMessage(commandEvent: CommandEvent)

}