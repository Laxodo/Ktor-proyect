package org.example.project.aplicacion.categoria

import org.example.project.aplicacion.producto.listar.ProductoDto
import org.example.project.dominio.ICategoriaRepository

class GetProductosFromCategoriaUseCase(private val repository: ICategoriaRepository) {
    suspend fun invoke(id: String): List<ProductoDto>{
        val items = repository.getProductsCategoria(id)
        return items
    }
}