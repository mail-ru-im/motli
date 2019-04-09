package ru.mail.im.motli.resource

import org.junit.Test

import org.junit.Assert.*
import ru.mail.im.motli.config.AppConfig
import ru.mail.im.motli.config.AppConfigDto
import java.io.File

class ThemeSetTest {

    @Test
    fun getTheme() {
        assertNotNull(ThemeSet(config("green")).getTheme("green"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun nonExistingTheme() {
        ThemeSet(config("blue")).getTheme("green")
    }

    private fun config(vararg themes: String) = AppConfig(
            File("."),
            AppConfigDto(null, null, null, themes.asList(), null))
}