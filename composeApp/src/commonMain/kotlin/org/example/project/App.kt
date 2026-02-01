package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource

import ktorproyect.composeapp.generated.resources.Res
import ktorproyect.composeapp.generated.resources.compose_multiplatform
import org.example.project.ui.Main
import org.example.project.ui.categoria.list.CategoriaList
import org.example.project.ui.categoria.list.CategoriaListViewModel

@Composable
@Preview
fun App() {
    Main()
}