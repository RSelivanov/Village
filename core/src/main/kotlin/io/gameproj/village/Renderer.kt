package io.gameproj.village

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import io.gameproj.village.world.*
import ktx.assets.toInternalFile
import ktx.graphics.use

class Renderer(
    private val world: World,
    private val batch: SpriteBatch,
    private val camera: OrthographicCamera
) {

    private val tileSize = 64f

    // Словарь текстур для земли
    private val groundTextures = mapOf(
        GroundType.GRASS to Texture("tiles/grass.png".toInternalFile()),
        GroundType.WATER to Texture("tiles/water.png".toInternalFile()),
        GroundType.LAVA to Texture("tiles/lava.png".toInternalFile()),
        GroundType.POISON to Texture("tiles/poison.png".toInternalFile()),
        GroundType.VOID to Texture("tiles/void.png".toInternalFile())
    )

    // Словарь текстур для блокеров (объектов на земле)
    private val blockerTextures = mapOf(
        BlockerType.TREE to Texture("tiles/tree.png".toInternalFile()),
        BlockerType.ROCK to Texture("tiles/rock.png".toInternalFile()),
        BlockerType.WALL to Texture("tiles/wall.png".toInternalFile()),
        BlockerType.BUSH to Texture("tiles/bush.png".toInternalFile())
    )

    fun render(entities: List<Entity>) {
        camera.update()
        batch.projectionMatrix = camera.combined

        batch.use {
            for (y in 0 until world.height) {
                for (x in 0 until world.width) {
                    val cell = world.getCell(x, y)

                    // 1. Земля
                    val groundTexture = groundTextures[cell.ground] ?: continue
                    it.draw(groundTexture, x * tileSize, y * tileSize, tileSize, tileSize)

                    // 2. Блокер, если есть
                    cell.blocker?.let { blocker ->
                        val blockerTexture = blockerTextures[blocker]
                        blockerTexture?.let {
                            batch.draw(it, x * tileSize, y * tileSize, tileSize, tileSize)
                        }
                    }
                }
            }

            // 3. Сущности
            for (entity in entities) {
                it.draw(entity.texture, entity.posX * tileSize, entity.posY * tileSize, tileSize, tileSize)
            }
        }
    }

    fun dispose() {
        groundTextures.values.forEach { it.dispose() }
        blockerTextures.values.forEach { it.dispose() }
    }
}
