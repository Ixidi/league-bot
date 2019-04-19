package me.ixidi.leaguebot.embed

interface FooterHandler {

    companion object {
        val EMPTY = object : FooterHandler {
            override fun footer(): String {
                return ""
            }
        }
    }

    fun footer(): String

}