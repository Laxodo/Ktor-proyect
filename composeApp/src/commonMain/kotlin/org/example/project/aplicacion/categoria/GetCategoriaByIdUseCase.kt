package org.example.project.aplicacion.categoria

import org.example.project.aplicacion.categoria.listar.CategoriaDto
import org.example.project.dominio.ICategoriaRepository

class GetCategoriaByIdUseCase(private val repository: ICategoriaRepository) {
    suspend fun invoke(id: String): CategoriaDto{
        val items = repository.findById(id)
        return items
    }
}