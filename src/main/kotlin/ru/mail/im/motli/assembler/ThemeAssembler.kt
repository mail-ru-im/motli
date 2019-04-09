package ru.mail.im.motli.assembler

import ru.mail.im.motli.NamingConvention
import ru.mail.im.motli.config.AppConfig
import ru.mail.im.motli.resource.AbstractResource
import ru.mail.im.motli.resource.Theme
import java.util.*

class ThemeAssembler(private var theme: Theme,
                     private val config: AppConfig,
                     private var naming: NamingConvention) {

    private val colorAttributes = ArrayList<Attribute>()
    private val colorStateListAttributes = ArrayList<Attribute>()
    private val drawableAttributes = ArrayList<Attribute>()

    fun assemble(fileSet: ResourceFileSet) {
        assembleColors(fileSet)
        assembleColorStateLists(fileSet)
        assembleDrawables(fileSet)
        assembleAttributes(fileSet)
        assembleStyle(fileSet)
    }

    private fun assembleColors(fileSet: ResourceFileSet) {
        val sorted = toSortedList(theme.colors)
        val file = ResourceFile(naming.getColorFileName(theme.name), "values")
        fileSet.addFile(file)

        file.begin()
        sorted.forEach {
            val colorName = naming.getColorName(it.name, theme.name)
            file.addLine(1, "<color name=\"$colorName\">${it.value}</color>")
            colorAttributes.add(Attribute(naming.getColorAttributeName(it.name), "@color/$colorName"))
        }
        file.end()
    }

    private fun assembleColorStateLists(fileSet: ResourceFileSet) {
        val sorted = toSortedList(theme.colorStateLists)
        sorted.forEach {
            val colorName = naming.getColorStateListName(it.name, theme.name)
            val file = ResourceFile("$colorName.xml", "color" + it.qualifiers)
            file.addText(it.content)
            fileSet.addFile(file)
            if (it.qualifiers.isEmpty()) {
                colorStateListAttributes.add(Attribute(naming.getColorStateListAttributeName(it.name), "@color/$colorName"))
            }
        }
    }

    private fun assembleDrawables(fileSet: ResourceFileSet) {
        val sorted = toSortedList(theme.drawables)
        sorted.forEach {
            val drawableName = naming.getDrawableName(it.name, theme.name)
            val file = ResourceFile("$drawableName.xml", "drawable" + it.qualifiers)
            file.addText(it.content)
            fileSet.addFile(file)
            if (it.qualifiers.isEmpty()) {
                drawableAttributes.add(Attribute(naming.getDrawableAttributeName(it.name), "@drawable/$drawableName"))
            }
        }
    }

    private fun assembleAttributes(fileSet: ResourceFileSet) {
        val attrs = ResourceFile("attrs.xml", "values")
        attrs.begin()
        attrs.addLine(1, "<declare-styleable name=\"GeneratedAttrs\">")

        addAttributeDeclaration(attrs, "COLOR", "color", colorAttributes)
        attrs.addLine()
        addAttributeDeclaration(attrs, "COLOR STATE LISTS", "color", colorStateListAttributes)
        attrs.addLine()
        addAttributeDeclaration(attrs, "DRAWABLES", "reference", drawableAttributes)

        attrs.addLine(1, "</declare-styleable>")
        attrs.end()
        fileSet.addFile(attrs)
    }

    private fun assembleStyle(fileSet: ResourceFileSet) {
        val themeFile = ResourceFile(naming.getStyleFileName(theme.name), "values")
        themeFile.begin()
        themeFile.addLine(1, "<style name=\"${naming.getStyleName(theme.name)}\" parent=\"${config.parentTheme}\">")

        addAttributeMapping(themeFile, "COLORS", colorAttributes)
        themeFile.addLine()
        addAttributeMapping(themeFile, "COLOR STATE LISTS", colorStateListAttributes)
        themeFile.addLine()
        addAttributeMapping(themeFile, "DRAWABLES", drawableAttributes)

        themeFile.addLine(1, "</style>")
        themeFile.end()
        fileSet.addFile(themeFile)
    }

    private fun addAttributeDeclaration(file: ResourceFile, blockName: String, format: String, attributes: List<Attribute>) {
        file.addLine(2, "<!-- $blockName -->")
        attributes.forEach { file.addLine(2, "<attr name=\"${it.name}\" format=\"$format\" />") }
        file.addLine(2, "<!-- END OF $blockName -->")
    }

    private fun addAttributeMapping(file: ResourceFile, blockName: String, attributes: List<Attribute>) {
        file.addLine(2, "<!-- $blockName -->")
        attributes.forEach { file.addLine(2, "<item name=\"${it.name}\">${it.value}</item>") }
        file.addLine(2, "<!-- END OF $blockName -->")
    }

    private fun <T : AbstractResource> toSortedList(list: List<T>): List<T> {
        val resourceComparator = Comparator<AbstractResource> { r1, r2 -> r1.name.compareTo(r2.name) }
        val result = ArrayList(list)
        result.sortWith(resourceComparator)
        return result
    }

    data class Attribute(val name: String, val value: String)
}