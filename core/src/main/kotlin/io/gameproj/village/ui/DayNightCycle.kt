package io.gameproj.village.time

class DayNightCycle(
    private val dayDuration: Float = 60f,   // длина дня в секундах
    private val nightDuration: Float = 20f  // длина ночи в секундах
) {
    private var timer = 0f
    private var isDay = true

    fun update(delta: Float) {
        timer += delta
        val maxTime = if (isDay) dayDuration else nightDuration

        if (timer >= maxTime) {
            timer = 0f
            isDay = !isDay
        }
    }

    fun getDarknessAlpha(): Float {
        val maxDarkness = 0.4f
        val phaseTime = if (isDay) dayDuration else nightDuration
        val progress = timer / phaseTime

        return if (isDay) {
            // Утро → полдень → вечер
            if (progress < 0.3f) {
                (progress / 0.3f) * maxDarkness // плавное затемнение утром
            } else if (progress > 0.7f) {
                ((1f - progress) / 0.3f) * maxDarkness // плавное затемнение вечером
            } else {
                0f // день — светло
            }
        } else {
            // Ночь → темно
            maxDarkness * (1f - Math.abs(0.5f - progress) * 2f) // темнее к середине ночи
        }
    }

    fun isDayTime(): Boolean = isDay
    fun isNightTime(): Boolean = !isDay
}
