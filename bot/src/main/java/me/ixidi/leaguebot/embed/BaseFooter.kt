package me.ixidi.leaguebot.embed

class BaseFooter(val text: String): FooterHandler {

    override fun footer(): String {
        return text
    }

}