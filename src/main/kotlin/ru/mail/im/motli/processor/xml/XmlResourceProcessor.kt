package ru.mail.im.motli.processor.xml

import ru.mail.im.motli.config.AppConfig
import ru.mail.im.motli.processor.ResourceProcessor
import ru.mail.im.motli.resource.AbstractResource
import ru.mail.im.motli.resource.ThemeSet
import java.io.File
import java.io.FileFilter

abstract class XmlResourceProcessor(protected val config: AppConfig,
                                    private val directory: String) : ResourceProcessor {

    private val placeholderPattern = Regex("\\$\\{([a-zA-Z]+)\\.([a-zA-Z_]+)}")

    abstract fun createResource(name: String, content: String, qualifiers: String): AbstractResource

    abstract fun createResolver(resolverName: String): ResourceResolver

    override fun fill(themes: ThemeSet) {
        val directory = File(config.dataDirectory, directory)
        val drawables = directory.listFiles(FileFilter { !it.isDirectory && it.name.endsWith(".xml") })
        drawables?.forEach { processFile(it, themes) }
    }

    private fun processFile(file: File, themes: ThemeSet) {
        val content = file.readText()
        val qualifiers = extractQualifiers(file)
        config.themes.forEach { themeName ->
            val output = placeholderPattern.replace(content) {
                createResolver(it.groupValues[1]).resolve(it.groupValues[2], themeName)
            }
            themes.getTheme(themeName).putResource(createResource(extractName(file), output, qualifiers))
        }
    }

    private fun extractQualifiers(file: File): String {
        val name = file.nameWithoutExtension
        val index = name.indexOf('-')
        return if (index == -1) "" else name.substring(index)
    }

    private fun extractName(file: File): String {
        val name = file.nameWithoutExtension
        val index = name.indexOf('-')
        return if (index == -1) name else name.substring(0, index)
    }
}