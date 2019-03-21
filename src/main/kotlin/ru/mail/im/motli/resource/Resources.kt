package ru.mail.im.motli.resource

abstract class AbstractResource(val name: String)

class Color(name: String, val value: String) : AbstractResource(name)

class ColorStateList(name: String, val content: String, val qualifiers: String = "") : AbstractResource(name)

class Drawable(name: String, val content: String, val qualifiers: String = "") : AbstractResource(name)