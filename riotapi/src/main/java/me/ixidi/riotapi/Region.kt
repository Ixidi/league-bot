package me.ixidi.riotapi

enum class Region(
        val commonName: String,
        val fullName: String,
        val value: String
) {

    EUNE("eune", "Europe Nordic & East", "eun1"),
    EUW("euw", "Europe West", "euw1");

    companion object {

        fun match(string: String): Region? {
            return values().firstOrNull { it.commonName == string.toLowerCase() }
        }

    }

    fun getLink(): String {
        return "https://$value.api.riotgames.com"
    }

}