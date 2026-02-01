package org.example.project.infraestructura.repositories

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.aplicacion.categoria.crear.CrearCategoriaCommand
import org.example.project.aplicacion.categoria.listar.CategoriaDto
import org.example.project.aplicacion.producto.listar.ProductoDto
import org.example.project.dominio.ICategoriaRepository
import org.example.project.infraestructura.entity.CategoriaWithProducto

class CategoryRepository (private val url:String,private val _client: HttpClient):
    ICategoriaRepository {
    override suspend fun all(): MutableList<CategoriaDto> {
        val request = this._client.get(url)
        val items: List<CategoriaDto> = request.body()
        return items.toMutableList()
    }

    override suspend fun getProductsCategoria(id: String): List<ProductoDto> {
        val request = this._client.get(url + "/" + id + "/productos")
        val item: CategoriaWithProducto = request.body<CategoriaWithProducto>()
        return CategoriaWithProducto.toDto(item)
    }

    override suspend fun create(item: CrearCategoriaCommand) {
        this._client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(item)
        }
    }

    override suspend fun delete(id: String) {
        this._client.delete(url + "/" + id)
    }

    override suspend fun findById(id: String): CategoriaDto {
        val request = this._client.get(url + "/" + id)
        val item: CategoriaDto = request.body()
        return item
    }
}