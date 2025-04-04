package io.gameproj.village

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.assets.toInternalFile
import ktx.graphics.use

class Renderer(
    private val map: Map,
    private val batch: SpriteBatch,
    private val camera: OrthographicCamera
    ) {

    private val tileSize = map.getTileSize()
    private val mapWidth = map.getWidth()
    private val mapHeight = map.getHeight()

    private val tile = Texture("tiles/grass.png".toInternalFile())
    //private val character = Texture("units/characterA.png".toInternalFile())

    fun render(entities: List<Entity>) {
        camera.update()
        batch.projectionMatrix = camera.combined

        batch.use {
            // карта
            for (y in 0 until mapHeight) {
                for (x in 0 until mapWidth) {
                    it.draw(tile, x * tileSize, y * tileSize, tileSize, tileSize)
                }
            }

            // Рисуем всех сущностей
            for (entity in entities) {
                it.draw(entity.texture, entity.posX * map.getTileSize(), entity.posY * map.getTileSize(), map.getTileSize(), map.getTileSize())
            }
        }
    }

    fun dispose() {
        tile.dispose()
        //entities.forEach { it.dispose() }
    }
}
