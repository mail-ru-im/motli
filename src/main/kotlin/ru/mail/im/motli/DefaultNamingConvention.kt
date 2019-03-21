package ru.mail.im.motli

class DefaultNamingConvention : NamingConvention {

    override fun getStyleFileName(theme: String) = "theme_$theme.xml"

    override fun getStyleName(theme: String) = toUpperCamelCase(theme) + "ThemeGenerated"

    override fun getColorFileName(theme: String) = "colors_$theme.xml"

    override fun getColorAttributeName(colorName: String) = "color" + toUpperCamelCase(colorName)

    override fun getColorName(colorName: String, theme: String) = colorName + "_" + theme

    override fun getColorStateListName(colorName: String, theme: String) = colorName + "_" + theme

    override fun getColorStateListAttributeName(colorName: String) = "color" + toUpperCamelCase(colorName)

    override fun getDrawableName(drawableName: String, theme: String) = drawableName + "_" + theme

    override fun getDrawableAttributeName(drawableName: String) = "drawable" + toUpperCamelCase(drawableName)

    private fun toUpperCamelCase(name: String): String {
        val builder = StringBuilder()
        var toUpper = true
        name.forEach { ch ->
            when {
                toUpper -> {
                    builder.append(Character.toUpperCase(ch))
                    toUpper = false
                }
                ch == '_' -> toUpper = true
                else -> builder.append(ch)
            }
        }
        return builder.toString()
    }
}