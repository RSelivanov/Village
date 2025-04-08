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
import io.gameproj.village.entity.UnitEntity
import io.gameproj.village.time.DayNightCycle

import io.gameproj.village.world.World


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
    var timeOfDay = 0f
    val maxDarkness = 0.6f // Не больше!

    // Окно игры и камера
    private val virtualWidth = 1920f
    private val virtualHeight = 1080f
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(virtualWidth, virtualHeight, camera)
    private val cameraController = CameraController(camera)

    // Создаем мир ( генерируем )
    private val world = World(64, 64)

    // Создаем объект для отрисовки 2D-графики (спрайтов) (из библиотеки)
    private val batch = SpriteBatch()
    // Создаем объект для отрисовки наших страйтов
    private val renderer = Renderer(world, batch, camera)

    // Создаем объект цикла дня и ночи
    private val dayNightCycle = DayNightCycle()

    private val entities = listOf(
        UnitEntity(world, 5, 5, "units/Human.png"),
        UnitEntity(world, 10, 10, "units/Zombie.png")
    )

    // Тут инициализация всякая
    init {
        Gdx.input.inputProcessor = cameraController

        // Отправляем сущности на рендер
    }

    // Срабатывает 60 раз в секунду
    override fun render(delta: Float)
    {
        clearScreen(0.0f, 0.0f, 0.0f)

        renderer.update(entities)
        dayNightCycle.update(delta)




        timer += delta
        if (timer >= stepTime) { timer = 0f;  secondUpdate(); }
        timeOfDay = (timeOfDay + delta * 0.01f) % 1f
    }

    // Срабатывает 1 раз в секунду
    fun secondUpdate()
    {
        for (entity in entities)
        {
            entity.secondUpdate()
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
