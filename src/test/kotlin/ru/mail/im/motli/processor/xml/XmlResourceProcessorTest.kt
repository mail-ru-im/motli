package ru.mail.im.motli.processor.xml

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import ru.mail.im.motli.config.AppConfig
import ru.mail.im.motli.config.AppConfigDto
import ru.mail.im.motli.resource.Drawable
import ru.mail.im.motli.resource.ThemeSet
import java.io.File

class XmlResourceProcessorTest {

    @get:Rule
    var tmpFolder = TemporaryFolder()

    lateinit var config: AppConfig

    lateinit var baseDir: File

    @Before
    fun setUp() {
        baseDir = tmpFolder.newFolder()

        config = AppConfig(baseDir, AppConfigDto(".",
                null,
                null,
                listOf("green", "blue"),
                null))
    }

    @Test
    fun fill() {
        val themes = process("foo.xml", "Hello \${test.world}")
        listOf("green", "blue").forEach {
            val drawables = themes.getTheme(it).drawables
            assertEquals("foo", drawables.first().name)
            assertEquals("Hello $it world", drawables.first().content)
        }
    }

    @Test
    fun fillWithQualifiers() {
        val themes = process("bar-sw600dp-land.xml", "Hello \${test.world}")
        listOf("green", "blue").forEach {
            val drawables = themes.getTheme(it).drawables
            assertEquals("-sw600dp-land", drawables.first().qualifiers)
        }
    }

    private fun process(name: String, content: String) : ThemeSet {
        File(baseDir, name).writeText(content)
        val themes = ThemeSet(config)
        TestProcessor(config).fill(themes)
        return themes
    }

    private inner class TestProcessor(config: AppConfig) : XmlResourceProcessor(config, ".") {

        override fun createResource(name: String, content: String, qualifiers: String) =
                Drawable(name, content, qualifiers)

        override fun createResolver(resolverName: String) = object : ResourceResolver {
            override fun resolve(resourceName: String, theme: String) = "$theme $resourceName"
        }
    }
}