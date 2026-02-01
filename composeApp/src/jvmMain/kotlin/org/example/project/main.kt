package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.project.di.moduloAplicacion
import org.example.project.di.moduloDominio
import org.example.project.di.moduloInfraestructuran
import org.example.project.di.moduloUI
import org.koin.core.context.GlobalContext.startKoin

fun main() = application {
    startKoin {
        modules(moduloAplicacion, moduloDominio, moduloUI, moduloInfraestructuran)
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Ktor proyect",
    ) {
        App()
    }
}