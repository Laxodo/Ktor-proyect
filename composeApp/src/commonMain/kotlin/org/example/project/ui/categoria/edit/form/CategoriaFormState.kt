package org.example.project.ui.categoria.edit.form

data class CategoriaFormState(
    val nombre: String = "",
    val descripcion: String = "",
    val activo: Boolean = false,

    val nameError: String? = null,
    val descriptionError: String? = null,
    
    val submitted: Boolean = false
)