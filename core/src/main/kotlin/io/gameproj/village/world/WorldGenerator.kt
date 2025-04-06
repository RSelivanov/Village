package io.gameproj.village.world

import kotlin.random.Random

object WorldGenerator {

    fun generate(width: Int, height: Int): Array<Array<Cell>> {
        val grid = Array(height) { y ->
            Array(width) { x ->

                // Генерация земли
                val ground = when (Random.nextInt(100)) {
                    in 0..5 -> GroundType.WATER      // 6%
                    in 6..8 -> GroundType.LAVA       // 3%
                    in 9..12 -> GroundType.POISON    // 4%
                    else -> GroundType.GRASS         // остальное
                }

                // Генерация блокеров
                val blocker = when (Random.nextInt(100)) {
                    in 0..7 -> BlockerType.TREE      // 8%
                    in 8..9 -> BlockerType.ROCK      // 2%
                    in 10..11 -> BlockerType.BUSH     // 2%
                    else -> null                    // 90% пусто
                }

                Cell(ground = ground, blocker = blocker)
            }
        }

        return grid
    }
}
