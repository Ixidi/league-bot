package me.ixidi.leaguebot.listener

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.ixidi.leaguebot.function.veryfication.VerificationManager
import me.ixidi.leaguebot.util.ActionHandler
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter

class ReactionListener: ListenerAdapter() {

    override fun onPrivateMessageReactionAdd(event: PrivateMessageReactionAddEvent) {
        val verification = VerificationManager[event.user.idLong]
        if (verification != null) {
            ActionHandler.handleSuspend(event) {
                verification.handle(event)
            }
        }
    }
}