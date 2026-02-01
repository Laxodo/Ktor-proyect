package org.example.project.aplicacion.producto.crear

import kotlinx.serialization.Serializable

@Serializable
data class AddProductoCommand (
    val nombre : String,
    val descripcion : String,
    val precio : String,
    val categoriaId: String,
    val activo : Boolean
)
