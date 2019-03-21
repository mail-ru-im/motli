package ru.mail.im.motli.assembler

import ru.mail.im.motli.NamingConvention
import ru.mail.im.motli.config.AppConfig
import ru.mail.im.motli.resource.ResourceSet

class ResourceAssembler(private val config: AppConfig, private val naming: NamingConvention) {

    fun assemble(resources: ResourceSet): ResourceFileSet {
        verifyResource(resources)
        val result = ResourceFileSet()
        config.themes.forEach{ ThemeAssembler(resources.getTheme(it), config, naming).assemble(result) }
        return result
    }

    private fun verifyResource(resources: ResourceSet) {
        val themes = config.themes.map { resources.getTheme(it) }

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