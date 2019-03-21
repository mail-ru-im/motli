package ru.mail.im.motli.processor.xml

import ru.mail.im.motli.NamingConvention
import ru.mail.im.motli.config.AppConfig
import ru.mail.im.motli.resource.Drawable

class DrawableProcessor(config: AppConfig, private val namingConvention: NamingConvention)
    : XmlResourceProcessor(config, "drawable") {

    override fun createResolver(resolverName: String): ResourceResolver {
        return when (resolverName) {
            "COLOR" -> ColorResolver(namingConvention)
            "DRAWABLE" -> DrawableResolver(namingConvention)
            else -> throw IllegalArgumentException("Unknown resolver $resolverName")
        }
    }

    override fun createResource(name: String, content: String, qualifiers: String)
            = Drawable(name, content, qualifiers)
}