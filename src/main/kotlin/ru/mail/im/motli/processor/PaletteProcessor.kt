package ru.mail.im.motli.processor

import com.google.gson.Gson
import ru.mail.im.motli.config.AppConfig
import ru.mail.im.motli.resource.Color
import ru.mail.im.motli.resource.ThemeSet
import java.io.FileReader

class PaletteProcessor(val config: AppConfig) : ResourceProcessor {

    override fun fill(themes: ThemeSet) {
        val dto = Gson().fromJson(FileReader(config.paletteFile), PaletteDto::class.java)
        checkVersion(dto.version)
        config.themes.forEach {
            val theme = themes.getTheme(it)
            val colors = dto.themes[theme.name]
                    ?: throw IllegalArgumentException("Theme ${theme.name} not found in palette")
            colors.forEach { name, hexColor -> theme.putResource(Color(name, hexColor)) }
        }
    }

    private fun checkVersion(version: Int) {
        if (version != 1) throw IllegalArgumentException("Version $version not supported")
    }

    internal data class PaletteDto(val version: Int,
                                   val themes: Map<String, Map<String, String>>)
}