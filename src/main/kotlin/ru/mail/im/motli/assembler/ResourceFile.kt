package ru.mail.im.motli.assembler

class ResourceFile(val name: String, val folder: String = "") {

    private val lines = ArrayList<String>()

    val text: String
        get() = lines.joinToString(separator = "\n")

    fun begin() {
        addLine(0, "<?xml version=\"1.0\" encoding=\"utf-8\"?>")
        addLine(0, "<resources>")
    }

    fun end() {
        addLine(0, "</resources>")
    }

    fun addLine() {
        lines.add("")
    }

    fun addLine(indent: Int, line: String) {
        val builder = StringBuilder()
        for (i in 0 until indent) {
            builder.append("    ")
        }
        builder.append(line)
        lines.add(builder.toString())
    }

    fun addText(text: String) {
        lines.addAll(text.split('\n'))
    }

    override fun toString() = "File '$name'"
}