package org.example.project.aplicacion.producto

import org.example.project.aplicacion.categoria.listar.CategoriaDto
import org.example.project.dominio.IProductoRepository

class GetCategoriaFromProductoUseCase(private val repository: IProductoRepository) {
    suspend fun invoke(id: String): CategoriaDto{
        val item = repository.getCategory(id)
        return item
    }
}