package org.example.project.aplicacion.producto

import org.example.project.aplicacion.producto.listar.ProductoDto
import org.example.project.dominio.IProductoRepository

class GetProductoByIdUseCase(private val repository: IProductoRepository) {
    suspend fun invoke(id: String): ProductoDto{
        val item = repository.findById(id)
        return item
    }
}