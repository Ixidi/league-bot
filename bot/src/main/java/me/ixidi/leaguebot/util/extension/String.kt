package me.ixidi.leaguebot.util.extension

import com.besaba.revonline.pastebinapi.impl.factory.PastebinFactory
import com.besaba.revonline.pastebinapi.paste.PasteExpire
import com.besaba.revonline.pastebinapi.paste.PasteVisiblity
import me.ixidi.leaguebot.LeagueBot
import me.ixidi.riotapi.Region
import me.ixidi.riotapi.encode

private val factory = PastebinFactory()
private val pastebin = factory.createPastebin(LeagueBot.config.getString("pastebinApiCode")!!)

fun String.postOnPastebin(title: String): String {
    val builder = factory.createPaste()
    builder.setMachineFriendlyLanguage("text")
    builder.setTitle(title)
    builder.setRaw(this)
    builder.setVisiblity(PasteVisiblity.Private)
    builder.setExpire(PasteExpire.Never)

    val response = pastebin.post(builder.build())
    return response.get()
}

fun String.avatarUrl(region: Region): String {
    return LeagueBot.config.getString("avatarsUrl")!!.replace("{REGION}", region.value).replace("{SUMMONER}", this.replace(" ", "+"))
}

fun String.replaceArgs(vararg args: Pair<String, String>): String {
    var string = this
    args.forEach {
        string = string.replace("{${it.first.toUpperCase()}}", it.second)
    }
    return string
}