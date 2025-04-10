package io.gameproj.village

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import io.gameproj.village.world.*
import ktx.assets.toInternalFile
import ktx.graphics.use
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import io.gameproj.village.entity.Entity

class Renderer(
    private val world: World,
    private val batch: SpriteBatch,
    private val camera: OrthographicCamera
) {

    private val tileSize = 64f
    private val shapeRenderer = ShapeRenderer()

    // ВАЖНО! Мы тут подгружаем  текстуры один раз при создании экземпляра класса Renderer
    private val groundTextures = mapOf(
        GroundType.GRASS to Texture("tiles/grass.png".toInternalFile()),
        GroundType.WATER to Texture("tiles/water.png".toInternalFile()),
        GroundType.LAVA to Texture("tiles/lava.png".toInternalFile()),
        GroundType.POISON to Texture("tiles/poison.png".toInternalFile()),
        GroundType.VOID to Texture("tiles/void.png".toInternalFile())
    )

    private val entityTextures = mapOf(
        EntityType.TREE to Texture("tiles/tree.png".toInternalFile()),
        EntityType.ROCK to Texture("tiles/rock.png".toInternalFile()),
        EntityType.WALL to Texture("tiles/wall.png".toInternalFile()),
        EntityType.BUSH to Texture("tiles/bush.png".toInternalFile())
    )

    // Срабатывает 60 раз в секунду
    fun update(entities: List<Entity>) {
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
                    cell.entity?.let { blocker ->
                        val blockerTexture = entityTextures[blocker]
                        blockerTexture?.let {
                            batch.draw(it, x * tileSize, y * tileSize, tileSize, tileSize)
                        }
                    }

                    // 3. Сущность
                    /*cell.entity?.let { entity ->
                        it.draw(entity.texture, x * tileSize, y * tileSize, tileSize, tileSize)
                    }*/
                }
            }

            // 3. Сущности
            /*
            for (entity in entities) {
                it.draw(entity.texture, entity.posX * tileSize, entity.posY * tileSize, tileSize, tileSize)
            }
            */

        }


    }

    fun NightEffect()
    {
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        // а затем — затемнение
        shapeRenderer.projectionMatrix = camera.combined
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color(0.1f, 0.1f, 0.2f, 0.4f) // полупрозрачное затемнение
        shapeRenderer.rect(
        camera.position.x - camera.viewportWidth / 2f * camera.zoom,
        camera.position.y - camera.viewportHeight / 2f * camera.zoom,
        camera.viewportWidth * camera.zoom,
        camera.viewportHeight * camera.zoom
        )
        shapeRenderer.end()
        Gdx.gl.glDisable(GL20.GL_BLEND)
    }

    fun dispose() {
        groundTextures.values.forEach { it.dispose() }
        entityTextures.values.forEach { it.dispose() }
    }
}
