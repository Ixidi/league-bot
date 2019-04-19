package me.ixidi.leaguebot.data.message

import me.ixidi.leaguebot.util.BotException
import me.ixidi.leaguebot.data.Language
import me.ixidi.leaguebot.data.yaml.YamlFile
import me.ixidi.leaguebot.util.extension.replaceArgs
import java.io.File

object MessageManager {

    private val messages = HashMap<Language, YamlFile>()

    fun loadAll(directory: File? = null) {
        Language.values().forEach { lang ->
            val name = "messages_${lang.code}.yml"
            val yaml = YamlFile(if (directory == null) File(name) else File(directory, name))
            yaml.load()
            messages[lang] = yaml
        }
    }

    operator fun get(language: Language, key: String, vararg args: Pair<String, String>): String {
        val lang = messages[language] ?: throw BotException("${language.englishName} language has not been loaded!")
        val message = lang.getString(key) ?: throw BotException("${language.englishName} language does not contain key $key!")
        return message.replaceArgs(*args)
    }

}