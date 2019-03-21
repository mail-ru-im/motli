package ru.mail.im.motli.resource

import ru.mail.im.motli.config.AppConfig

class ResourceSet(config: AppConfig) {

    private val themes = config.themes.associate { it to Theme(it) }

    fun getTheme(name: String) = themes[name] ?: throw IllegalArgumentException("Unsupported theme $name")
}
