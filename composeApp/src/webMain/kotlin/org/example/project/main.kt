package org.example.project

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import org.example.project.di.moduloAplicacion
import org.example.project.di.moduloDominio
import org.example.project.di.moduloInfraestructuran
import org.example.project.di.moduloUI
import org.koin.core.context.startKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin {
        modules(moduloAplicacion, moduloDominio, moduloUI, moduloInfraestructuran)
    }
    ComposeViewport {
        App()
    }
}