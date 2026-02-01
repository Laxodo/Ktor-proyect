package org.example.project.aplicacion.categoria.listar

import kotlinx.serialization.Serializable

@Serializable
data class CategoriaDto (
    val id: String,
    val nombre: String,
    val descripcion: String,
    val activo: Boolean
)