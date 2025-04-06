package io.gameproj.village

import io.gameproj.village.world.World
import io.gameproj.village.world.Cell

class UnitEntity(
    private val world: World,
    posX: Int,
    posY: Int,
    spritePath: String,
) : Entity(posX, posY, spritePath) {

    fun update() {
        val directions = listOf(
            1 to 0,   // вправо
            -1 to 0,  // влево
            0 to 1,   // вверх
            0 to -1   // вниз
        ).shuffled()

        for ((dx, dy) in directions) {
            val nextX = posX + dx
            val nextY = posY + dy

            if (world.isInside(nextX, nextY)) {
                val cell = world.getCell(nextX, nextY)

                if (cell.isWalkable()) {
                    posX = nextX
                    posY = nextY
                    break
                }
            }
        }
    }
}
