package ru.mail.im.motli.processor

import ru.mail.im.motli.resource.ResourceSet

interface ResourceProcessor {
    fun fill(resources : ResourceSet)
}