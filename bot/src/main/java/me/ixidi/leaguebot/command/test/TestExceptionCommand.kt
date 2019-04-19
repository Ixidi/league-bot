package me.ixidi.leaguebot.command.test

import me.ixidi.leaguebot.command.CommandEvent
import me.ixidi.leaguebot.command.PrivateCommand
import me.ixidi.leaguebot.function.veryfication.VerificationManager
import net.dv8tion.jda.core.events.message.MessageReceivedEvent

class TestExceptionCommand: PrivateCommand() {

    class TestException(message: String): Exception(message)

    override suspend fun executePrivateMessage(commandEvent: CommandEvent) {
        VerificationManager.start(commandEvent.event.author)
        throw TestException("Test exception, as planned :)")
    }

}