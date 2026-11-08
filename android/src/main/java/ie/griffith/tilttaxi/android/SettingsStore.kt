package ie.griffith.tilttaxi.android

import android.content.Context

class SettingsStore(context: Context) {
    private val preferences = context.applicationContext
        .getSharedPreferences("drive_settings", Context.MODE_PRIVATE)

    val useTilt: Boolean
        get() = preferences.getBoolean("use_tilt", true)

    val sensitivityLevel: Int
        get() = preferences.getInt("sensitivity", 1).coerceIn(0, 2)

    val flipPedals: Boolean
        get() = preferences.getBoolean("flip_pedals", false)

    fun save(
        useTilt: Boolean,
        sensitivityLevel: Int,
        flipPedals: Boolean
    ) {
        preferences.edit()
            .putBoolean("use_tilt", useTilt)
            .putInt("sensitivity", sensitivityLevel.coerceIn(0, 2))
            .putBoolean("flip_pedals", flipPedals)
            .apply()
    }
}
