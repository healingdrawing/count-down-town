@file:JvmName("Lwjgl3Launcher")

package io.github.healingdrawing.countdowntown.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import io.guthub.healingdrawing.countdowntown.ABC
import io.guthub.healingdrawing.countdowntown.lwjgl3.DesktopTimer


/** Launches the desktop (LWJGL3) application.  */
object Lwjgl3Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        createApplication()
    }

    private fun createApplication(): Lwjgl3Application {
        return Lwjgl3Application(ABC(DesktopTimer()), defaultConfiguration)
    }

    private val defaultConfiguration: Lwjgl3ApplicationConfiguration
        private get() {
            val configuration = Lwjgl3ApplicationConfiguration()
            configuration.setTitle("CDT")
            configuration.setWindowedMode(360, 640)
            configuration.setWindowIcon("icon128.png", "icon64.png", "icon32.png", "icon16.png")
            return configuration
        }
}
