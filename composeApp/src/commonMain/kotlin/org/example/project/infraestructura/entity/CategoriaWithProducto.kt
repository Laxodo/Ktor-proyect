package org.example.project.infraestructura.entity

import kotlinx.serialization.Serializable
import org.example.project.aplicacion.producto.listar.ProductoDto

@Serializable
data class CategoriaWithProducto(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val activo: Boolean,
    val productos: List<ProductoDto>
) {
    companion object{
        public fun toDto(item: CategoriaWithProducto): List<ProductoDto> {
            return item.productos.map { it ->
                ProductoDto(
                    id = it.id,
                    nombre = it.nombre,
                    descripcion = it.descripcion,
                    precio = it.precio,
                    activo = it.activo
                )
            }
        }
    }
}