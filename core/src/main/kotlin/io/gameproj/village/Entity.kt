package io.gameproj.village

import com.badlogic.gdx.graphics.Texture
import ktx.assets.toInternalFile

open class Entity(
    var posX: Int,
    var posY: Int,
    spritePath: String
) {
    var texture: Texture = Texture(spritePath.toInternalFile())

    open fun dispose() {
        texture.dispose()
    }
}
