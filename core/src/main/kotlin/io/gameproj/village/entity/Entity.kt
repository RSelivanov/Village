package io.gameproj.village.entity

import com.badlogic.gdx.graphics.Texture
import ktx.assets.toInternalFile

abstract class Entity(
    var posX: Int,
    var posY: Int,
    spritePath: String,
) {
    var texture: Texture = Texture(spritePath.toInternalFile())

    open fun dispose() {
        texture.dispose()
    }

    open fun interact() {
        println("Стоит бездействет")
    }

    open fun log( text : String) {
        print("log to file $text")
    }

}
