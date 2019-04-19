package me.ixidi.leaguebot.function.veryfication.step

import me.ixidi.leaguebot.data.Language
import me.ixidi.leaguebot.embed.FooterHandler
import me.ixidi.leaguebot.function.veryfication.Verification
import me.ixidi.leaguebot.function.veryfication.VerificationStep
import me.ixidi.leaguebot.user.LeagueUserManager
import me.ixidi.leaguebot.util.extension.infoEmbed
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.entities.User
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent

class InfoStep(verification: Verification, private val user: User): VerificationStep(verification) {

    private var _message: Message? = null
    override val message: Message?
        get() = _message

    override fun send() {
        val beforeMessage = _message
        if (beforeMessage != null) {
            beforeMessage.delete().complete()
            _message = null
        }
        val channel = user.openPrivateChannel().complete()
        val lang = LeagueUserManager.get(user).language
        val message = channel.infoEmbed(lang["verification.first.title"], lang["verification.first.text"])
        _message = message
        Language.values().forEach {
            message.addReaction(it.flagUnicode).queue()
        }
    }

    override suspend fun onReaction(event: PrivateMessageReactionAddEvent) {
        val lang = Language.values().firstOrNull { it.flagUnicode == event.reactionEmote.name } ?: return
        val leagueUser = LeagueUserManager.get(user)
        if (leagueUser.language == lang) {
            return
        }
        leagueUser.language = lang
        //event.channel.successEmbed(lang["settingsUpdated"], "${lang.flagUnicode} ${lang["languageChanged"]}")
        send()
    }

    override suspend fun onPrivateMessage(event: MessageReceivedEvent) {
        message?.reactions?.forEach { it.removeReaction().queue() }
        verification.nextStep(false)!!.onPrivateMessage(event)
    }

}