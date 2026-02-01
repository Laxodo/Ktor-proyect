package org.example.project.aplicacion.categoria.listar

import org.example.project.dominio.ICategoriaRepository

class GetAllCategoriasUseCase(private val repository: ICategoriaRepository) {
    suspend fun invoke(): List<CategoriaDto>{
        val items = repository.all().toList()
        return items
    }
}