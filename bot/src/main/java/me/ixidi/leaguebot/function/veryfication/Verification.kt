package me.ixidi.leaguebot.function.veryfication

import me.ixidi.leaguebot.function.veryfication.step.AccountStep
import me.ixidi.leaguebot.function.veryfication.step.InfoStep
import me.ixidi.leaguebot.user.LeagueUser
import me.ixidi.leaguebot.util.ActionHandler
import me.ixidi.leaguebot.util.BotException
import net.dv8tion.jda.core.entities.User
import net.dv8tion.jda.core.events.Event
import net.dv8tion.jda.core.events.emote.EmoteAddedEvent
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent
import java.util.*

class Verification(user: User) {

    private val queue = ArrayDeque<VerificationStep>(listOf(
            InfoStep(this, user),
            AccountStep(this, user)
    ))

    var step: VerificationStep? = null
        private set

    fun nextStep(invokeSend: Boolean = true): VerificationStep? {
        step = queue.poll()
        if (invokeSend) {
            step?.send()
        }
        return step
    }

    suspend fun handle(event: Event): Boolean {
        val step = step ?: throw BotException("Step is null!")
        when (event) {
            is MessageReceivedEvent -> {
                if (event.channelType.isGuild) {
                    return false
                }
                step.onPrivateMessage(event)
                return true
            }
            is PrivateMessageReactionAddEvent -> {
                if (event.messageIdLong != step.message?.idLong ?: throw BotException("Message in verification cannot be null!")) {
                    return false
                }
                step.onReaction(event)
                return true
            }
            else -> return false
        }
    }

}