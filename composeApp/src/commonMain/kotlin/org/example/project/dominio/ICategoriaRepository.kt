package org.example.project.dominio

import org.example.project.aplicacion.categoria.crear.CrearCategoriaCommand
import org.example.project.aplicacion.categoria.listar.CategoriaDto
import org.example.project.aplicacion.producto.listar.ProductoDto

interface ICategoriaRepository {
    suspend fun all(): MutableList<CategoriaDto>
    suspend fun getProductsCategoria(id: String): List<ProductoDto>
    suspend fun create(item: CrearCategoriaCommand)
    suspend fun delete(id: String)
    suspend fun findById(id: String): CategoriaDto
}