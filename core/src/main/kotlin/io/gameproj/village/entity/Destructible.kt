package io.gameproj.village.entity

interface Destructible {
    var health: Int
    fun takeDamage(amount: Int)
}
