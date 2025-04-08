package io.gameproj.village.world

import io.gameproj.village.entity.Entity

// Основной тип земли
enum class GroundType(val walkable: Boolean, val effect: CellEffect?) {
    GRASS(true, null),
    WATER(true, CellEffect.WET),
    LAVA(true, CellEffect.BURNING),
    POISON(true, CellEffect.POISONED),
    VOID(false, null);
}

// Эффекты, накладываемые на юнита при входе в клетку
enum class CellEffect {
    WET,
    BURNING,
    POISONED
}

enum class EntityType(val blocker: Boolean) {
    HUMAN(true),
    ZOMBIE(true),
    TREE(true),
    WALL(true),
    ROCK(true),
    BUSH(false);
}

// Основной класс клетки
data class Cell(
    var ground: GroundType = GroundType.GRASS,
    var entity: EntityType? = null
) {
    // Можно ли войти в эту клетку?
    fun isWalkable(): Boolean {
        if (entity != null && !entity!!.blocker) return true
        return ground.walkable
    }

    // Какой эффект накладывается при входе?
    fun getEffectOnEnter(): CellEffect? {
        return ground.effect
    }

    // Очистить клетку от юнита (например, если он ушёл или умер)
    fun clearOccupant() {
        entity = null
    }
}
