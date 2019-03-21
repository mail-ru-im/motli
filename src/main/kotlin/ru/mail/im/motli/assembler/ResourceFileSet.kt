package ru.mail.im.motli.assembler

class ResourceFileSet {
    private val _files = mutableMapOf<String, ResourceFile>()

    val files: List<ResourceFile>
        get() = _files.values.toList()

    fun addFile(file: ResourceFile) {
        _files["${file.folder}/${file.name}"] = file
    }
}