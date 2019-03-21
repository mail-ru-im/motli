package ru.mail.im.motli

interface NamingConvention {
    fun getStyleFileName(theme: String): String

    fun getStyleName(theme: String): String

    fun getColorFileName(theme: String): String

    fun getColorAttributeName(colorName: String): String

    fun getColorName(colorName: String, theme: String): String

    fun getColorStateListName(colorName: String, theme: String): String

    fun getColorStateListAttributeName(colorName: String): String

    fun getDrawableName(drawableName: String, theme: String): String

    fun getDrawableAttributeName(drawableName: String): String
}