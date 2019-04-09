package ru.mail.im.motli.assembler

import ru.mail.im.motli.NamingConvention
import ru.mail.im.motli.config.AppConfig
import ru.mail.im.motli.resource.ThemeSet

class ResourceAssembler(private val config: AppConfig, private val naming: NamingConvention) {

    fun assemble(themes: ThemeSet): ResourceFileSet {
        verifyThemes(themes)
        val result = ResourceFileSet()
        config.themes.forEach{ ThemeAssembler(themes.getTheme(it), config, naming).assemble(result) }
        return result
    }

    private fun verifyThemes(themeSet: ThemeSet) {
        val themes = config.themes.map { themeSet.getTheme(it) }

        if (themes.map { it.colors.size }.toSet().size != 1) {
            throw IllegalArgumentException("All themes should contain the same number of colors")
        }

        if (themes.map { it.drawables.size }.toSet().size != 1) {
            throw IllegalArgumentException("All themes should contain the same number of drawables")
        }

        if (themes.map { it.colorStateLists.size }.toSet().size != 1) {
            throw IllegalArgumentException("All themes should contain the same number of color state lists")
        }
    }
}