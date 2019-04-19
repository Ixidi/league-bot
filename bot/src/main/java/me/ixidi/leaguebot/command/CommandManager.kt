package me.ixidi.leaguebot.command

import me.ixidi.leaguebot.LeagueBot
import me.ixidi.leaguebot.util.ActionHandler
import net.dv8tion.jda.core.events.message.MessageReceivedEvent

object CommandManager {

    val prefix by lazy {
        val prefix = LeagueBot.config.getString("prefix")!!
        if (!LeagueBot.debug) prefix else prefix + prefix
    }

    private val commands = HashMap<String, Command>()

    fun add(vararg commands: Pair<String, Command>) {
        commands.forEach {
            this.commands[it.first] = it.second
        }
    }

    fun handle(event: MessageReceivedEvent) {
        val time = System.currentTimeMillis()
        val split = event.message.contentRaw.split(" ")
        val name = split[0].substring(CommandManager.prefix.length)
        if (name.isEmpty()) {
            return
        }

        val command = commands[name] ?: return
        val args = if (split.size == 1) emptyList() else split.subList(1, split.size )
        val commandEvent = CommandEvent(event, time, args)
        ActionHandler.handleSuspend(event, commandEvent) {
            command.execute(commandEvent)
        }
    }
}