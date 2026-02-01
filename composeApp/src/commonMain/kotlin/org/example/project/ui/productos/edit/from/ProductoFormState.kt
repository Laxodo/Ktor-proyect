package org.example.project.ui.productos.edit.from

import org.example.project.aplicacion.categoria.listar.CategoriaDto

data class ProductoFormState (
    val nombre: String = "",
    val descripcion: String = "",
    val precio: String = "",
    val categoria: CategoriaDto? = null,
    val activo: Boolean = false,
    val nameError: String? = "",
    val descripcionError: String? = "",
    val precioError: String? = "",
    val submitted: Boolean = false
)