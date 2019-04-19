package me.ixidi.leaguebot.util

abstract class Enableable {

    private var _enabled = false
    val enabled: Boolean
        get() = _enabled

    fun enable() {
        if (enabled) {
            throw IllegalStateException()
        }
        onEnable()
        _enabled = true
    }

    protected abstract fun onEnable()

}