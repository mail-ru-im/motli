package ru.mail.im.motli.processor.xml

import ru.mail.im.motli.NamingConvention
import ru.mail.im.motli.config.AppConfig
import ru.mail.im.motli.resource.ColorStateList

class ColorStateListProcessor(config: AppConfig, private val namingConvention: NamingConvention)
    : XmlResourceProcessor(config, "color") {

    override fun createResolver(resolverName: String): ResourceResolver {
        return when (resolverName) {
            "COLOR" -> ColorResolver(namingConvention)
            else -> throw IllegalArgumentException("Unknown resolver $resolverName")
        }
    }

    override fun createResource(name: String, content: String, qualifiers: String)
            = ColorStateList(name, content, qualifiers)
}