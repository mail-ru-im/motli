package ru.mail.im.motli.config

import java.io.File
import java.util.Collections

class AppConfig(private val baseDirectory: File,
                private val dto: AppConfigDto) {
    val themes = dto.themes ?: Collections.emptyList()
    val parentTheme = dto.parentTheme

    val dataDirectory: File
        get() = File(baseDirectory, dto.dataDirectory).absoluteFile

    val outputDirectory: File
        get() = File(baseDirectory, dto.outputDirectory).absoluteFile

    val paletteFile: File
        get() = File(baseDirectory, dto.paletteFile)

    fun verify() {
        if (themes.isEmpty()) {
            throw IllegalArgumentException("No themes")
        }
        if (parentTheme.isNullOrEmpty()) {
            throw IllegalArgumentException("No parent theme")
        }
        if (dto.dataDirectory.isNullOrEmpty()) {
            throw IllegalArgumentException("No data directory")
        }
        if (dto.outputDirectory.isNullOrEmpty()) {
            throw IllegalArgumentException("No output directory")
        }
        if (dto.paletteFile.isNullOrEmpty()) {
            throw IllegalArgumentException("No palette file")
        }

        if (!dataDirectory.exists()) {
            throw IllegalArgumentException("Data directory '${dataDirectory.absolutePath}' not exists")
        }
        if (!paletteFile.exists()) {
            throw IllegalArgumentException("Palette file '${paletteFile.absolutePath}' not found")
        }
    }
}
