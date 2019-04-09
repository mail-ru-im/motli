package ru.mail.im.motli.processor

import ru.mail.im.motli.resource.ThemeSet

interface ResourceProcessor {
    fun fill(themes : ThemeSet)
}