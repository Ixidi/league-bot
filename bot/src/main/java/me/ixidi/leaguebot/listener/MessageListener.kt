package me.ixidi.leaguebot.listener

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.ixidi.leaguebot.command.CommandManager
import me.ixidi.leaguebot.function.veryfication.VerificationManager
import me.ixidi.leaguebot.util.ActionHandler
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter

class MessageListener: ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val message = event.message
        val text = message.contentRaw

        //verification
        val verification = VerificationManager[event.author.idLong]
        if (verification != null) {
            ActionHandler.handleSuspend(event) {
                verification.handle(event)
            }
            return
        }

        //command
        if (text.startsWith(CommandManager.prefix)) {
            CommandManager.handle(event)
            return
        }
    }

}