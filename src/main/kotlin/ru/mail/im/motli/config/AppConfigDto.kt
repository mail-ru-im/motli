package ru.mail.im.motli.config

data class AppConfigDto(val dataDirectory: String?,
                        val paletteFile: String?,
                        val outputDirectory: String?,
                        val themes: List<String>?,
                        val parentTheme: String?)
