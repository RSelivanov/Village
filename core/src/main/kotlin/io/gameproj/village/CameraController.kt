package io.gameproj.village

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.Gdx

class CameraController(private val camera: OrthographicCamera) : InputAdapter() {

    private var isDragging = false
    private var lastX = 0f
    private var lastY = 0f

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (button == Input.Buttons.RIGHT) {
            isDragging = true
            lastX = screenX.toFloat()
            lastY = screenY.toFloat()
        }
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        if (isDragging) {
            val deltaX = screenX - lastX
            val deltaY = screenY - lastY
            lastX = screenX.toFloat()
            lastY = screenY.toFloat()

            camera.translate(-deltaX * camera.zoom, deltaY * camera.zoom)
        }
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (button == Input.Buttons.RIGHT) {
            isDragging = false
        }
        return false
    }

    override fun keyDown(keycode: Int): Boolean {
        val speed = 10f * camera.zoom

        when (keycode) {
            Input.Keys.W -> camera.translate(0f, speed)
            Input.Keys.S -> camera.translate(0f, -speed)
            Input.Keys.A -> camera.translate(-speed, 0f)
            Input.Keys.D -> camera.translate(speed, 0f)
        }
        return false
    }

    // Tile size default render on screen 32x32, zoom max: 64x64, zoom min 16x16
    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        camera.zoom += amountY * 0.1f
        camera.zoom = camera.zoom.coerceIn(0.5f, 2f)
        return true
    }

}
