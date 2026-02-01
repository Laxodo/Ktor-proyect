package org.example.project.infraestructura.repositories

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.aplicacion.categoria.listar.CategoriaDto
import org.example.project.aplicacion.producto.crear.AddProductoCommand
import org.example.project.aplicacion.producto.listar.ProductoDto
import org.example.project.dominio.IProductoRepository

class ProductoRepository(private val url:String,private val _client: HttpClient): IProductoRepository {
    override suspend fun all(): MutableList<ProductoDto> {
        val request = this._client.get(url)
        val items: List<ProductoDto> = request.body<List<ProductoDto>>()
        return items.toMutableList()
    }

    override suspend fun getCategory(id: String): CategoriaDto {
        val request = this._client.get(url + "/" + id + "/categoria")
        val item: CategoriaDto = request.body()
        return item
    }

    override suspend fun create(item: AddProductoCommand) {
        this._client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(item)
        }
    }

    override suspend fun delete(id: String) {
        this._client.delete(url + "/" + id)
    }

    override suspend fun findById(id: String): ProductoDto {
        val request = this._client.get(url + "/" + id)
        val item: ProductoDto = request.body()
        return item
    }
}