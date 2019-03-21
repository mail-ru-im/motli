package ru.mail.im.motli.processor

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import ru.mail.im.motli.config.AppConfig
import ru.mail.im.motli.config.AppConfigDto
import ru.mail.im.motli.resource.ResourceSet
import java.nio.charset.StandardCharsets


class PaletteProcessorTest {

    @get:Rule
    var tmpFolder = TemporaryFolder()

    lateinit var config: AppConfig

    @Before
    fun setUp() {
        val paletteFile = tmpFolder.newFile()
        paletteFile.writeText("""
            {
                "version": 1,
                "themes": {
                    "green": {
                        "primary": "#FF00FF",
                        "secondary": "#00FF00"
                    },
                    "blue": {
                        "primary": "#AABBCC",
                        "secondary": "#CCBBAA"
                    }
                }
            }
        """.trimIndent(), StandardCharsets.UTF_8)

        config = AppConfig(paletteFile.parentFile, AppConfigDto(null,
                paletteFile.name,
                null,
                listOf("green", "blue"),
                null))
    }

    @Test
    fun fill() {
        val resources = ResourceSet()
        PaletteProcessor(config).fill(resources)

        var theme = resources.getTheme("green")
        assertEquals(2, theme.colors.size)
        assertEquals("primary", theme.colors[0].name)
        assertEquals("#FF00FF", theme.colors[0].value)
        assertEquals("secondary", theme.colors[1].name)
        assertEquals("#00FF00", theme.colors[1].value)

        theme = resources.getTheme("blue")
        assertEquals(2, theme.colors.size)
        assertEquals("primary", theme.colors[0].name)
        assertEquals("#AABBCC", theme.colors[0].value)
        assertEquals("secondary", theme.colors[1].name)
        assertEquals("#CCBBAA", theme.colors[1].value)
    }
}