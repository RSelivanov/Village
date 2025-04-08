package io.gameproj.village.entity

import io.gameproj.village.world.World

class UnitEntity(
    private val world: World,
    posX: Int,
    posY: Int,
    spritePath: String,
) : Entity(posX, posY, spritePath), UnitInteract {

    fun secondUpdate() {
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

    override fun interact() {
        println("Лечить")
    }

    override fun attac() {
        println("attac")
    }

    override fun run() {
        println("run")
    }

    override fun sleep() {
        println("sleep")
    }


}
