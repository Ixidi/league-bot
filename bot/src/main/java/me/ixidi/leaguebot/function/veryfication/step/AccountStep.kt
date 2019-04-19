package me.ixidi.leaguebot.function.veryfication.step

import me.ixidi.leaguebot.LeagueBot
import me.ixidi.leaguebot.data.Language
import me.ixidi.leaguebot.embed.FooterHandler
import me.ixidi.leaguebot.embed.error.ErrorEmbed
import me.ixidi.leaguebot.embed.info.InformationEmbed
import me.ixidi.leaguebot.function.veryfication.Verification
import me.ixidi.leaguebot.function.veryfication.VerificationManager
import me.ixidi.leaguebot.function.veryfication.VerificationStep
import me.ixidi.leaguebot.user.LeagueUserManager
import me.ixidi.leaguebot.util.BotException
import me.ixidi.leaguebot.util.extension.errorEmbed
import me.ixidi.leaguebot.util.extension.infoEmbed
import me.ixidi.leaguebot.util.extension.successEmbed
import me.ixidi.leaguebot.util.extension.summonerEmbed
import me.ixidi.riotapi.Region
import me.ixidi.riotapi.call.Status
import me.ixidi.riotapi.call.summoner.dto.Summoner
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.entities.User
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent
import java.util.concurrent.ThreadLocalRandom

private const val EMOJI = "\u2705"

class AccountStep(verification: Verification, private val user: User): VerificationStep(verification) {

    private var summonerInfoMessage: Message? = null

    private lateinit var region: Region
    private lateinit var summoner: Summoner
    private var requiredIconId = -1

    private var _message: Message? = null
    override val message: Message?
        get() = _message

    override fun send() {}

    override suspend fun onReaction(event: PrivateMessageReactionAddEvent) {
        if (summonerInfoMessage == null) {
            return
        }
        if (event.reactionEmote.name != EMOJI) {
            return
        }

        val summonerResponse = LeagueBot.riotApi.summonerByAccount(region, summoner.accountId)
        when (summonerResponse.status) {
            Status.OK -> {
                val summoner = summonerResponse.body!!
                val error = summoner.profileIconId != requiredIconId
                clear(false)
                if (error) {
                    sendMessage(error)
                    return
                }
                val lang = LeagueUserManager[event.user].language
                event.channel.successEmbed(lang["verification.end.ok.title"], lang["verification.end.ok.text"])
                VerificationManager.end(user.idLong)
            }
            else -> {
                throw BotException("Summoner response status is ${summonerResponse.status}.")
            }
        }
    }

    override suspend fun onPrivateMessage(event: MessageReceivedEvent) {
        clear(true)

        val channel = event.privateChannel
        val lang = LeagueUserManager[event.author].language
        val split = event.message.contentRaw.split(" ")
        if (split.size < 2) {
            val summoner = lang["word.summoner"]
            val region = lang["word.region"]
            val usage = lang["correctUsage", "usage" to "$region $summoner"]
            channel.errorEmbed(lang["error"], usage)
            return
        }

        val region = Region.match(split[0])
        if (region == null) {
            channel.errorEmbed(lang["error"], lang["unknownRegion"])
            return
        }

        val summonerName = split.subList(1, split.size).joinToString(" ")

        val summonerResponse = LeagueBot.riotApi.summonerByName(region, summonerName)
        when (summonerResponse.status) {
            Status.OK -> {
                val summoner = summonerResponse.body!!

                val leagueResponse = LeagueBot.riotApi.leaguePositionBySummoner(region, summoner.id)
                if (leagueResponse.status != Status.OK) {
                    throw BotException("League response status is ${summonerResponse.status}.")
                }
                summonerInfoMessage = channel.summonerEmbed(region, lang, summoner, leagueResponse.body!!)
                this.summoner = summoner
                this.region = region
                sendMessage(false)
            }
            Status.DATA_NOT_FOUND -> {
                channel.errorEmbed(lang["error"], lang["unknownSummoner"])
            }
            else -> {
                throw BotException("Summoner response status is ${summonerResponse.status}.")
            }
        }
    }

    private fun sendMessage(error: Boolean) {
        val channel = user.openPrivateChannel().complete()
        val lang = LeagueUserManager[user].language
        if (requiredIconId == -1) {
            do {
                requiredIconId = ThreadLocalRandom.current().nextInt(0, 29)
            } while (requiredIconId == summoner.profileIconId)
        }
        val iconLink = LeagueBot.riotApi.dataDragon.iconLink(region, requiredIconId)
        val embed = if (!error) {
            InformationEmbed(channel, lang["verification.second.title"], FooterHandler.EMPTY, lang["verification.second.text"], thumbnailUrl = iconLink)
        } else {
            ErrorEmbed(channel, lang["error"], FooterHandler.EMPTY, lang["verification.end.error.text"], thumbnailUrl = iconLink)
        }
        val message = embed.send()
        message.addReaction(EMOJI).queue()
        _message = message
    }

    private fun clear(summoner: Boolean) {
        val beforeMessage = message
        if (beforeMessage != null)  {
            if (summoner) {
                summonerInfoMessage?.delete()?.complete()
                summonerInfoMessage = null
                requiredIconId = -1
            }
            beforeMessage.delete().complete()
            _message = null
        }
    }

}