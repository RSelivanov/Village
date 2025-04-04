package io.gameproj.village

class Map (
    private val width: Int,
    private val height: Int,
    private val tileSize: Float
) {
    fun getWidth(): Int = width
    fun getHeight(): Int = height
    fun getTileSize(): Float = tileSize

    fun isInside(x: Int, y: Int): Boolean {
        return x in 0 until width && y in 0 until height
    }
}
