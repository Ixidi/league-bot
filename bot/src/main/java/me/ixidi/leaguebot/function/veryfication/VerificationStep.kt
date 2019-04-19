package me.ixidi.leaguebot.function.veryfication

import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent

abstract class VerificationStep(protected val verification: Verification) {

    abstract val message: Message?

    abstract fun send()

    abstract suspend fun onReaction(event: PrivateMessageReactionAddEvent)

    abstract suspend fun onPrivateMessage(event: MessageReceivedEvent)

}