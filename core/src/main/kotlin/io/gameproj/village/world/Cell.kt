package io.gameproj.village.world

import io.gameproj.village.Entity

// Основной тип земли
enum class GroundType(val walkable: Boolean, val effect: CellEffect?) {
    GRASS(true, null),
    WATER(true, CellEffect.WET),
    LAVA(true, CellEffect.BURNING),
    POISON(true, CellEffect.POISONED),
    VOID(false, null);

    fun toChar(): Char = when (this) {
        GRASS -> '.'
        WATER -> '~'
        LAVA -> '^'
        POISON -> '%'
        VOID -> '#'
    }
}

// Тип объектов, стоящих НА земле (блокеры)
enum class BlockerType(val walkable: Boolean) {
    TREE(false),
    WALL(false),
    ROCK(false),
    BUSH(true);

    fun toChar(): Char = when (this) {
        TREE -> 'T'
        WALL -> 'W'
        ROCK -> 'R'
        BUSH -> 'B'
    }
}

// Эффекты, накладываемые на юнита при входе в клетку
enum class CellEffect {
    WET,
    BURNING,
    POISONED
}

// Основной класс клетки
data class Cell(
    var ground: GroundType = GroundType.GRASS,
    var blocker: BlockerType? = null,
    var occupant: Entity? = null
) {
    // Можно ли войти в эту клетку?
    fun isWalkable(): Boolean {
        if (occupant != null) return false
        if (blocker != null && !blocker!!.walkable) return false
        return ground.walkable
    }

    // Какой эффект накладывается при входе?
    fun getEffectOnEnter(): CellEffect? {
        return ground.effect
    }

    // Очистить клетку от юнита (например, если он ушёл или умер)
    fun clearOccupant() {
        occupant = null
    }
}
