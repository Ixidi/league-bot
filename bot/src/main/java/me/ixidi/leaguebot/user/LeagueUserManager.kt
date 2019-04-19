package me.ixidi.leaguebot.user

import net.dv8tion.jda.core.entities.User

object LeagueUserManager {

    private val map = HashMap<Long, LeagueUser>()

    operator fun get(id: Long): LeagueUser {
        var leagueUser = map[id]
        if (leagueUser == null) {
            leagueUser = LeagueUser(id)
            map[id] = leagueUser
        }
        return leagueUser
    }

    operator fun get(user: User): LeagueUser {
        return LeagueUserManager[user.idLong]
    }
}