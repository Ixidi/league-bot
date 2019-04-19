package me.ixidi.leaguebot.data

import me.ixidi.leaguebot.data.message.MessageManager

enum class Language(
        val nativeName: String,
        val englishName: String,
        val code: String,
        val flagUnicode: String
        ) {

    ENGLISH("English", "English", "en", "\uD83C\uDDEC\uD83C\uDDE7"),
    POLISH("Polski", "Polish", "pl", "\uD83C\uDDF5\uD83C\uDDF1");

    operator fun get(key: String, vararg args: Pair<String, String>): String {
        return MessageManager.get(this, key, *args)
    }

}