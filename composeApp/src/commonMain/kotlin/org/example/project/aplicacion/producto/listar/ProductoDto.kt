package org.example.project.aplicacion.producto.listar

import kotlinx.serialization.Serializable

@Serializable
data class ProductoDto (
    val id: String,
    val nombre : String,
    val descripcion : String,
    val precio : Float,
    val activo : Boolean
)