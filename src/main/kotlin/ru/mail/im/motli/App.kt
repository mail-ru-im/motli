package ru.mail.im.motli

import com.google.gson.Gson
import ru.mail.im.motli.assembler.ResourceAssembler
import ru.mail.im.motli.config.AppConfig
import ru.mail.im.motli.config.AppConfigDto
import ru.mail.im.motli.processor.PaletteProcessor
import ru.mail.im.motli.processor.xml.ColorStateListProcessor
import ru.mail.im.motli.processor.xml.DrawableProcessor
import ru.mail.im.motli.resource.ResourceSet
import java.io.File
import java.io.FileReader

fun main(args: Array<String>) {
    if (args.size != 1) {
        System.err.println("Path to config not provided")
        return
    }

    val configFile = File(args[0]).absoluteFile
    println("Parsing config ${configFile.absolutePath}")

    val configDto = Gson().fromJson(FileReader(configFile), AppConfigDto::class.java)
    val config = AppConfig(configFile.parentFile, configDto)
    config.verify()

    val resources = ResourceSet(config)

    System.out.println("Processing palette")
    PaletteProcessor(config).fill(resources)

    val namingConvention = DefaultNamingConvention()

    System.out.println("Processing color state list resources")
    ColorStateListProcessor(config, namingConvention).fill(resources)

    System.out.println("Processing drawable resources")
    DrawableProcessor(config, namingConvention).fill(resources)

    System.out.println("Assembling")
    val fileSet = ResourceAssembler(config, namingConvention).assemble(resources)

    System.out.println("Writing")
    ResourceWriter(config).write(fileSet)

    System.out.println("Done")
}
