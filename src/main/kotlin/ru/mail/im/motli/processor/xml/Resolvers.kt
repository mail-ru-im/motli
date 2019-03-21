package ru.mail.im.motli.processor.xml

import ru.mail.im.motli.NamingConvention

interface ResourceResolver {
    fun resolve(resourceName: String, theme: String): String
}

class ColorResolver(private val namingConvention: NamingConvention) : ResourceResolver {

    override fun resolve(resourceName: String, theme: String)
            = "@color/" + namingConvention.getColorName(resourceName, theme)
}

class DrawableResolver(private val namingConvention: NamingConvention) : ResourceResolver {

    override fun resolve(resourceName: String, theme: String)
            = "@drawable/" + namingConvention.getDrawableName(resourceName, theme)
}