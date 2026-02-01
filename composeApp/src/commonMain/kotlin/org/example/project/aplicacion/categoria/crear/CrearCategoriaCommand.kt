package org.example.project.aplicacion.categoria.crear

import kotlinx.serialization.Serializable

@Serializable
data class CrearCategoriaCommand (
    val nombre: String,
    val descripcion: String,
    val activo: Boolean
)