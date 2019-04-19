package me.ixidi.leaguebot.function.veryfication

import net.dv8tion.jda.core.entities.User

object VerificationManager {

    private val map = HashMap<Long, Verification>()

    operator fun get(id: Long): Verification? {
        return map[id]
    }

    fun end(id: Long) {
        map.remove(id)
    }

    fun start(user: User) {
        if (this[user.idLong] == null) {
            val verification = Verification(user)
            map[user.idLong] = verification
            verification.nextStep()
        }
    }

}