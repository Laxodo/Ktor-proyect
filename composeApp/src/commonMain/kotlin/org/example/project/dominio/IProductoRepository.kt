package org.example.project.dominio

import org.example.project.aplicacion.categoria.listar.CategoriaDto
import org.example.project.aplicacion.producto.crear.AddProductoCommand
import org.example.project.aplicacion.producto.listar.ProductoDto

interface IProductoRepository {
    suspend fun all(): MutableList<ProductoDto>
    suspend fun getCategory(id: String): CategoriaDto
    suspend fun create(item: AddProductoCommand)
    suspend fun delete(id: String)
    suspend fun findById(id: String): ProductoDto
}