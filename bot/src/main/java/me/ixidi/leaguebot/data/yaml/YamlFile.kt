package me.ixidi.leaguebot.data.yaml

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.error.YAMLException
import java.io.File
import java.lang.IllegalStateException
import java.math.BigInteger

class YamlFile(private val file: File, defaultFile: File? = null) {

    private val yaml = Yaml()
    private var map: MutableMap<String, Any> = HashMap()

    private val default = if (defaultFile != null) {
        val yaml = YamlFile(defaultFile)
        yaml.load()
        yaml
    } else {
        null
    }

    fun load() {
        if (!file.exists()) {
            file.createNewFile()
            if (default != null) {
                yaml.dump(default.map, file.printWriter())
            } else {
                return
            }
        }
        try {
            map = yaml.load<MutableMap<String, Any>>(file.inputStream())
        } catch (_: IllegalStateException) {}
    }

    fun save() {
        yaml.dump(map, file.printWriter())
    }

    fun getKeys(): Set<String> {
        return map.keys
    }

    fun get(key: String): Any? {
        val split = key.split(".")
        if (split.size == 1) {
            val value = map[key]
            if (value == null) {
                if (default != null) {
                    return default.get(key)
                }
                //TODO log
                return null
            }
            return value
        } else {
            val final = split.last()
            val sections = split.subList(0, split.size - 1)
            var section: Map<String, String>? = null
            sections.forEach {
                val s = section ?: map
                section = (s[it] ?: return null) as? LinkedHashMap<String, String> ?: return null
            }
            return section!![final]
        }
    }

    fun getString(key: String): String? {
        return get(key)?.toString() ?: return null
    }

    fun getInt(key: String): Int? {
        val value = get(key) ?: return null
        if (value is Int) return value
        throw YAMLException("Value of $key is not int.")
    }

    fun getLong(key: String): Long? {
        val value = get(key) ?: return null
        return when (value) {
            is Long -> value
            is Int -> value.toLong()
            else -> throw YAMLException("Value of $key is not long.")
        }
    }

    fun getBigInt(key: String): BigInteger? {
        val value = get(key) ?: return null
        return when (value) {
            is BigInteger -> value
            is Long -> BigInteger.valueOf(value)
            is Int -> BigInteger.valueOf(value.toLong())
            else -> throw YAMLException("Value of $key is not bigint.")
        }
    }

    fun getBoolean(key: String): Boolean? {
        val value = get(key) ?: return null
        if (value is Boolean) return value
        throw YAMLException("Value of $key is not boolean.")
    }

    fun getDouble(key: String): Double? {
        val value = get(key) ?: return null
        if (value is Double) return value
        throw YAMLException("Value of $key is not double.")
    }

}