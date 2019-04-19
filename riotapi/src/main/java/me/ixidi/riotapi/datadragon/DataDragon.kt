package me.ixidi.riotapi.datadragon

import me.ixidi.riotapi.Region

interface DataDragon {

    fun iconLink(region: Region, id: Int): String

}