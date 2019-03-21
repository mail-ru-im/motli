package ru.mail.im.motli.resource

class Theme(val name: String) {

    private val _colors = ArrayList<Color>()

    private val _drawables = ArrayList<Drawable>()

    private val _colorStateLists = ArrayList<ColorStateList>()

    val colors: List<Color>
        get() = _colors

    val drawables: List<Drawable>
        get() = _drawables

    val colorStateLists: List<ColorStateList>
        get() = _colorStateLists

    fun putResource(resource: AbstractResource) {
        when (resource) {
            is Color -> _colors.add(resource)
            is Drawable -> _drawables.add(resource)
            is ColorStateList -> _colorStateLists.add(resource)
            else -> throw IllegalArgumentException("Unsupported resource " + resource.javaClass.name)
        }
    }
}