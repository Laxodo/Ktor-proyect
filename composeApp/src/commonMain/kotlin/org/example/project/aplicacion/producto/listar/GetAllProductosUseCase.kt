package org.example.project.aplicacion.producto.listar

import org.example.project.dominio.IProductoRepository

class GetAllProductosUseCase(private val repository: IProductoRepository) {
    suspend fun invoke(): List<ProductoDto>{
        val items = repository.all().toList()
        return items
    }
}