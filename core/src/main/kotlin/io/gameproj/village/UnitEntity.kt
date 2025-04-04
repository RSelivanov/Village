package io.gameproj.village

class UnitEntity(
    private val map: Map,
    posX: Int,
    posY: Int,
    spritePath: String
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
            if (map.isInside(nextX, nextY)) {
                posX = nextX
                posY = nextY
                break
            }
        }
    }


}
