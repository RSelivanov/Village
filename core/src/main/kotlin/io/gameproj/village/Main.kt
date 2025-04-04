package io.gameproj.village

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.async.KtxAsync
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.Gdx


class Main : KtxGame<KtxScreen>() {
    override fun create() {
        KtxAsync.initiate()

        addScreen(FirstScreen())
        setScreen<FirstScreen>()
    }
}

class FirstScreen : KtxScreen {

    // Для создания метода update
    var timer = 0f
    val stepTime = 1f // каждые 1 секунду

    // Окно игры и камера
    private val virtualWidth = 1920f
    private val virtualHeight = 1080f
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(virtualWidth, virtualHeight, camera)
    private val cameraController = CameraController(camera)

    // Создаем объект карта мира
    private val map = Map(32, 32, 32f)

    // Создаем объект для отрисовки 2D-графики (спрайтов) (из библиотеки)
    private val batch = SpriteBatch()
    // Создаем объект для отрисовки наших страйтов
    private val renderer = Renderer(map, batch, camera)

    private val entities = listOf(
        UnitEntity(map, 5, 5, "units/Human.png"),
        UnitEntity(map, 10, 10, "units/Zombie.png")
    )

    // Тут инициализация всякая
    init
    {
        Gdx.input.inputProcessor = cameraController
    }

    // Срабатывает 60 раз в секунду
    override fun render(delta: Float)
    {
        clearScreen(0.7f, 0.7f, 0.7f)

        renderer.render(entities);


        timer += delta
        if (timer >= stepTime)
        {
            timer = 0f
            update()
        }
    }

    // Срабатывает 1 раз в секунду
    fun update()
    {
        for (entity in entities)
        {
            entity.update()
        }
    }

    override fun resize(width: Int, height: Int)
    {
        viewport.update(width, height)
    }

    override fun dispose()
    {
        batch.disposeSafely()
        renderer.dispose()
    }
}
