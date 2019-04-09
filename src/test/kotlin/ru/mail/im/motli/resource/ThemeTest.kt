package ru.mail.im.motli.resource

import org.junit.Test

import org.junit.Assert.*

class ThemeTest {

    private val theme = Theme("test")

    @Test
    fun putColorResource() {
        assertEquals(0, theme.colors.size)
        theme.putResource(Color("primary", "#FFFFFF"))
        assertEquals(1, theme.colors.size)
    }

    @Test
    fun putDrawableResource() {
        assertEquals(0, theme.drawables.size)
        theme.putResource(Drawable("btn_bg", ""))
        assertEquals(1, theme.drawables.size)
    }

    @Test
    fun putColorStateListResource() {
        assertEquals(0, theme.colorStateLists.size)
        theme.putResource(ColorStateList("border", ""))
        assertEquals(1, theme.colorStateLists.size)
    }

    @Test(expected = IllegalArgumentException::class)
    fun unknownResource() {
        theme.putResource(TestResource())
    }

    private inner class TestResource : AbstractResource("test")
}