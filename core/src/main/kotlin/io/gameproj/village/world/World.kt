package io.gameproj.village.world

class World(val width: Int, val height: Int) {
    val cells: Array<Array<Cell>> = WorldGenerator.generate(width, height)

    fun getCell(x: Int, y: Int): Cell = cells[y][x]


    fun isInside(x: Int, y: Int): Boolean {
        return x in 0 until width && y in 0 until height
    }
}
