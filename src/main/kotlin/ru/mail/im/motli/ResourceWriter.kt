package ru.mail.im.motli

import ru.mail.im.motli.assembler.ResourceFileSet
import ru.mail.im.motli.assembler.ResourceFile
import ru.mail.im.motli.config.AppConfig
import java.io.File

class ResourceWriter(val config: AppConfig) {
    fun write(fileSet: ResourceFileSet) {
        fileSet.files.forEach {
            writeFile(it)
        }
    }

    private fun writeFile(file: ResourceFile) {
        val folder = File(config.outputDirectory, file.folder)
        if (folder.exists() || folder.mkdirs()) {
            File(folder, file.name).writeText(file.text)
        }
    }
}