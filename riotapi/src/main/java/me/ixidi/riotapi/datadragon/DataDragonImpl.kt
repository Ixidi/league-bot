package me.ixidi.riotapi.datadragon

import khttp.get
import me.ixidi.riotapi.Adapter
import me.ixidi.riotapi.Region
import me.ixidi.riotapi.datadragon.dto.Realms

internal class DataDragonImpl: DataDragon {

    private val map = HashMap<Region, String>()

    init {
        val adapter = Adapter<Realms>(Realms::class.java)
        Region.values().forEach {
            val get = get("https://ddragon.leagueoflegends.com/realms/${it.commonName}.json")
            val realms = adapter.fromJson(get.text)
            map[it] = realms.version
        }
    }

    override fun iconLink(region: Region, id: Int): String {
        return "https://ddragon.leagueoflegends.com/cdn/${map[region]}/img/profileicon/$id.png"
    }

}