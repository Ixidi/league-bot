package me.ixidi.leaguebot.command

interface Command {

    suspend fun execute(commandEvent: CommandEvent)

}